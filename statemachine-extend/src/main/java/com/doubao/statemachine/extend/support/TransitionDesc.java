package com.doubao.statemachine.extend.support;

import com.doubao.statemachine.action.Action;
import com.doubao.statemachine.config.model.ChoiceData;
import com.doubao.statemachine.config.model.TransitionData;
import com.doubao.statemachine.guard.Guard;
import com.doubao.statemachine.transition.TransitionKind;
import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * transition desc
 *
 * @param <S>
 * @author doubao
 */
@Data
public class TransitionDesc<S, E, C> {
    private StateDesc<S> source;
    private StateDesc<S> target;
    private EventDesc<E> event;
    private GuardDesc guard;
    private List<ActionDesc> actions;
    private TransitionKind kind;

    public static final GuardDesc DEFAULT_GUARD = new GuardDesc("default", "default");

    public TransitionDesc(StateDesc<S> source, StateDesc<S> target, EventDesc<E> event, GuardDesc guard, List<ActionDesc> actions, TransitionKind kind) {
        this.source = source;
        this.target = target;
        this.event = event;
        this.guard = guard;
        this.actions = actions;
        this.kind = kind;
    }

    public TransitionDesc(TransitionData<S, E, C> transitionData,
                          Map<S, StateDesc<S>> stateDescMap,
                          Map<E, EventDesc<E>> eventDescMap,
                          Map<Class<? extends Guard>, GuardDesc> guardMap,
                          Map<Class<? extends Action>, ActionDesc> actionDescMap) {
        this.source = stateDescMap.get(transitionData.getSource());
        this.target = stateDescMap.get(transitionData.getTarget());
        this.event = eventDescMap.get(transitionData.getEvent());
        Guard<S, E, C> guard = transitionData.getGuard();
        this.guard = guard == null ? null : guardMap.get(guard.getClass());
        List<Action<S, E, C>> actions = transitionData.getActions();
        this.actions = actions == null ? Collections.emptyList() : actions.stream()
                .map(ac -> actionDescMap.get(ac.getClass()))
                .collect(Collectors.toList());
        this.kind = transitionData.getKind();
    }

    public TransitionDesc(ChoiceData<S, E, C> choiceData,
                          Map<S, StateDesc<S>> stateDescMap,
                          Map<Class<? extends Guard>, GuardDesc> guardMap,
                          Map<Class<? extends Action>, ActionDesc> actionDescMap) {
        this.source = stateDescMap.get(choiceData.getSource());
        this.target = stateDescMap.get(choiceData.getTarget());
        Guard<S, E, C> guard = choiceData.getGuard();
        this.guard = guard == null ? DEFAULT_GUARD : guardMap.getOrDefault(guard.getClass(), DEFAULT_GUARD);
        List<Action<S, E, C>> actions = choiceData.getActions();
        this.actions = actions == null ? Collections.emptyList() : actions.stream()
                .map(ac -> actionDescMap.get(ac.getClass()))
                .collect(Collectors.toList());
        this.kind = this.target == null ? TransitionKind.INTERNAL : TransitionKind.EXTERNAL;
    }
}