package com.doubao.statemachine.transition;

import com.doubao.statemachine.StateContext;
import com.doubao.statemachine.message.Message;
import com.doubao.statemachine.state.StateType;
import com.doubao.statemachine.state.history.HistoryState;
import com.doubao.statemachine.action.Action;
import com.doubao.statemachine.config.model.StatePersistModel;
import com.doubao.statemachine.guard.Guard;
import com.doubao.statemachine.state.State;

import java.util.List;

/**
 * external transition
 *
 * @author doubao
 * @date 2021/4/28
 */
public class ExternalTransition<S, E, C> extends AbstractTransition<S, E, C> {
    private final State<S, E, C> target;

    public ExternalTransition(State<S, E, C> source, E event,
                              Guard<S, E, C> guard,
                              List<Action<S, E, C>> actions, State<S, E, C> target) {
        super(source, event, guard, actions);
        this.target = target;
    }


    private boolean chainTransit(StateContext<S, E, C> context) {
        Message<S, E, C> message = context.getMessage();
        List<Transition<S, E, C>> transitions = transitionManager.selectMatchTransitions(
                Message.of(target.getId(), null, message.getContent()));
        for (Transition<S, E, C> transition : transitions) {
            context.setTransition(transition);
            interceptorManager.preProcessTransition(context);
            boolean transit = transition.transit(context);
            if (transit) {
                return true;
            }
        }
        eventPublisher.notifyTransitionNotFound(context.getMessage());
        return false;
    }


    private State<S, E, C> restoreHistory(StateContext<S, E, C> context) {
        HistoryState<S, E, C> historyState = (HistoryState<S, E, C>) target;
        StatePersistModel<S, E, C> secStatePersistModel = historyState.get(context);
        S state = secStatePersistModel.getState();
        return context.getStateMap().get(state);
    }

    @Override
    public boolean transit(StateContext<S, E, C> context) {
        eventPublisher.notifyTransitionStart(this, context.getMessage());
        if (!processGuard(context)) {
            return false;
        }

        //restore history
        StateType type = target.getType();
        if (type.equals(StateType.HISTORY)) {
            context.setTarget(restoreHistory(context));
        } else {
            context.setTarget(target);
        }
        processActions(context);
        //record history
        List<HistoryState<S, E, C>> historyStates = context.getHistoryStates();
        if (historyStates != null) {
            for (HistoryState<S, E, C> historyState : historyStates) {
                historyState.record(context);
            }
        }
        interceptorManager.postProcessTransition(context);
        eventPublisher.notifyTransitionSuccess(this, context.getMessage());
        //chain process
        while (context.getTarget() != null && context.getTarget().getType() != StateType.NORMAL) {
            if (!chainTransit(context)) {
                return false;
            }
        }
        return true;
    }


    @Override
    public State<S, E, C> getTarget() {
        return target;
    }

    @Override
    public TransitionKind getKind() {
        return TransitionKind.EXTERNAL;
    }
}
