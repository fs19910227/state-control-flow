package com.doubao.statemachine.core;

import com.doubao.statemachine.config.model.*;
import com.doubao.statemachine.state.ObjectState;
import com.doubao.statemachine.state.StateType;
import com.doubao.statemachine.state.history.HistoryState;
import com.doubao.statemachine.transition.ExternalTransition;
import com.doubao.statemachine.transition.Transition;
import com.doubao.statemachine.transition.TransitionKind;
import com.doubao.statemachine.action.Action;
import com.doubao.statemachine.config.model.*;
import com.doubao.statemachine.guard.Guard;
import com.doubao.statemachine.state.State;
import com.doubao.statemachine.state.history.HistoryRecorder;
import com.doubao.statemachine.transition.InternalTransition;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * StateMachineModelParser
 * use to pares stateMachineModel
 *
 * @author doubao
 * @date 2021/5/13
 */
public class StateMachineModelParser<S, E, C> {
    private final StateMachineModel<S, E, C> stateMachineModel;
    private final StatesData<S, E, C> statesData;
    private final TransitionsData<S, E, C> transitionsData;
    private final ConfigurationData<S, E, C> configurationData;
    private Map<S, State<S, E, C>> statesMap = new HashMap<>();
    private Map<S, List<Transition<S, E, C>>> transitionsMap = new HashMap<>();

    public StateMachineModelParser(StateMachineModel<S, E, C> stateMachineModel) {
        this.stateMachineModel = stateMachineModel;
        this.configurationData = stateMachineModel.getConfigurationData();
        this.transitionsData = stateMachineModel.getTransitionsData();
        this.statesData = stateMachineModel.getStatesData();
    }

    public void doParse() {
        //inherit configModel data from parent
        StateMachine<S, E, C> parent = configurationData.getParent();
        boolean inheritFromParent = configurationData.isInheritFromParent();
        if (parent != null && inheritFromParent) {
            ModelInheritProcessor<S, E, C> secModelInheritProcessor = new ModelInheritProcessor<>(parent.getModel(),
                    stateMachineModel);
            secModelInheritProcessor.inherit();
        }
        this.statesMap = this.buildStatesMap();
        this.transitionsMap = this.buildTransitionMap();
    }

    public Map<S, State<S, E, C>> getStatesMap() {
        return statesMap;
    }

    public Map<S, List<Transition<S, E, C>>> getTransitionsMap() {
        return transitionsMap;
    }

    /**
     * resolve events from transitions
     *
     * @param transitionsData transitions data
     * @param <S>             S
     * @param <E>             E
     * @param <C>             C
     * @return set EVENT
     */
    public static <S, E, C> Set<E> resolveEventsFromTransition(TransitionsData<S, E, C> transitionsData) {
        return transitionsData.getTransitionDataList().stream()
                .map(TransitionData::getEvent).collect(Collectors.toSet());
    }

    public static <S, E, C> Set<StateData<S, E, C>> resolveStatesFromTransition(
            TransitionsData<S, E, C> transitionsData) {
        Set<S> collect = transitionsData.getTransitionDataList().stream()
                .flatMap(t -> {
                    Set<S> s = new HashSet<>();
                    S source = t.getSource();
                    S target = t.getTarget();
                    s.add(source);
                    if (target != null) {
                        s.add(target);
                    }
                    return s.stream();
                }).collect(Collectors.toSet());
        transitionsData.getChoiceMap().forEach((s, list) -> {
            for (ChoiceData<S, E, C> secChoiceData : list) {
                S target = secChoiceData.getTarget();
                if (target != null) {
                    collect.add(target);
                }
            }
        });
        return collect.stream()
                .map(s -> new StateData<S, E, C>().setState(s).setType(StateType.NORMAL))
                .collect(Collectors.toSet());
    }

    private Map<S, State<S, E, C>> buildStatesMap() {
        Set<StateData<S, E, C>> statesByTransitions = resolveStatesFromTransition(transitionsData);
        Map<S, State<S, E, C>> stateMap = statesByTransitions.stream()
                .map(s -> new ObjectState<S, E, C>(s.getState(), s.getType()))
                .collect(Collectors.toMap(ObjectState::getId, s -> s));
        //process start
        StateData<S, E, C> start = statesData.getStart();
        if (start != null) {
            State<S, E, C> secState = stateMap.get(start.getState());
            StateType type = secState.getType();
            ObjectState<S, E, C> startState = new ObjectState<>(start.getState(), type);
            startState.setStart(true);
            stateMap.put(startState.getId(), startState);
        }
        Set<StateData<S, E, C>> finalList = statesData.getFinalSet();
        if (finalList != null) {
            for (StateData<S, E, C> end : finalList) {
                State<S, E, C> secState = stateMap.get(end.getState());
                StateType type = secState.getType();
                ObjectState<S, E, C> startState = new ObjectState<>(end.getState(), type);
                startState.setFinal(true);
                stateMap.put(startState.getId(), startState);
            }
        }
        //process choice
        for (StateData<S, E, C> choice : statesData.getChoices()) {
            ObjectState<S, E, C> choiceState = new ObjectState<>(choice.getState(), choice.getType());
            stateMap.put(choiceState.getId(), choiceState);
        }
        //process history
        HistoryRecorder<S, E, C> historyRecorder = statesData.getHistoryRecorder();
        for (StateData<S, E, C> history : statesData.getHistoryStates()) {
            HistoryState<S, E, C> historyState = new HistoryState<>(history.getState(), history.getRefStates(),
                    historyRecorder);
            stateMap.put(historyState.getId(), historyState);
        }
        //process entries
        Set<StateData<S, E, C>> entryStates = statesData.getEntryStates();
        for (StateData<S, E, C> entryState : entryStates) {
            ObjectState<S, E, C> entry = new ObjectState<>(entryState.getState(), entryState.getType());
            stateMap.put(entry.getId(), entry);
        }
        //process exits
        Set<StateData<S, E, C>> exitStates = statesData.getExitStates();
        for (StateData<S, E, C> exitState : exitStates) {
            ObjectState<S, E, C> entry = new ObjectState<>(exitState.getState(), exitState.getType());
            stateMap.put(entry.getId(), entry);
        }
        return stateMap;
    }

    private Map<S, List<Transition<S, E, C>>> buildTransitionMap() {
        List<TransitionData<S, E, C>> transitionDataList = transitionsData.getTransitionDataList();
        List<Transition<S, E, C>> transitionList = new ArrayList<>();
        //build transition
        for (TransitionData<S, E, C> secTransitionData : transitionDataList) {
            S source = secTransitionData.getSource();
            Guard<S, E, C> guard = secTransitionData.getGuard();
            List<Action<S, E, C>> actions = secTransitionData.getActions();
            S target = secTransitionData.getTarget();
            E event = secTransitionData.getEvent();
            Transition<S, E, C> resultTransition;
            switch (secTransitionData.getKind()) {
                case INTERNAL:
                    resultTransition = new InternalTransition<>(
                            getStateOrDefault(source, statesMap), event, guard, actions);
                    break;
                default:
                    resultTransition = new ExternalTransition<>(
                            getStateOrDefault(source, statesMap)
                            , event, guard, actions,
                            getStateOrDefault(target, statesMap)
                    );
                    break;
            }
            transitionList.add(resultTransition);
        }
        Map<S, List<Transition<S, E, C>>> transitionMap = transitionList.stream()
                .collect(groupingBy(t -> t.getSource().getId()));
        //build choice transition
        Map<S, List<ChoiceData<S, E, C>>> choicesMap = transitionsData.getChoiceMap();
        choicesMap.forEach((s, list) -> {
            List<Transition<S, E, C>> choiceList = list.stream().map(choice -> {
                S source = choice.getSource();
                List<Action<S, E, C>> actions = choice.getActions();
                Guard<S, E, C> guard = choice.getGuard();
                S target = choice.getTarget();
                if (target == null) {
                    return new InternalTransition<>(
                            getStateOrDefault(source, statesMap),
                            null, guard, actions);
                } else {
                    return new ExternalTransition<>(
                            getStateOrDefault(source, statesMap),
                            null, guard, actions,
                            getStateOrDefault(target, statesMap));
                }
            }).collect(toList());
            transitionMap.putIfAbsent(s, choiceList);
        });
        return transitionMap;
    }

    private State<S, E, C> getStateOrDefault(S state, Map<S, State<S, E, C>> stateMap) {
        return stateMap.getOrDefault(state, new ObjectState<>(state, StateType.NORMAL));
    }

    private static class ModelInheritProcessor<S, E, C> {
        private final StateMachineModel<S, E, C> parentModel;
        private final StateMachineModel<S, E, C> childModel;
        Map<S, Map<E, List<TransitionData<S, E, C>>>> childTransitionMap;
        Map<S, List<ChoiceData<S, E, C>>> childChoicesMap;

        public ModelInheritProcessor(StateMachineModel<S, E, C> parentModel,
                                     StateMachineModel<S, E, C> childModel) {
            this.parentModel = parentModel;
            this.childModel = childModel;
            TransitionsData<S, E, C> transitionsData = childModel.getTransitionsData();
            this.childTransitionMap = transitionsData.getTransitionDataList().stream()
                    .collect(groupingBy(TransitionData::getSource, groupingBy(TransitionData::getEvent)));
            this.childChoicesMap = transitionsData.getChoiceMap();
        }

        /**
         * copy transition data,discard actions
         *
         * @param secTransitionData
         * @return
         */
        private TransitionData<S, E, C> copyTransition(TransitionData<S, E, C> secTransitionData) {
            S source = secTransitionData.getSource();
            E event = secTransitionData.getEvent();
            S target = secTransitionData.getTarget();
            TransitionKind kind = secTransitionData.getKind();
            Guard<S, E, C> guard = secTransitionData.getGuard();
            List<Action<S, E, C>> actions = secTransitionData.getActions();
            return new TransitionData<S, E, C>()
                    .setSource(source)
                    .setEvent(event)
                    .setTarget(target)
                    .setGuard(guard)
                    .setActions(actions)
                    .setKind(kind);
        }

        private List<ChoiceData<S, E, C>> copyChoice(List<ChoiceData<S, E, C>> choiceDataList) {
            return choiceDataList.stream()
                    .map(c -> {
                        ChoiceData<S, E, C> secChoiceData = new ChoiceData<>();
                        secChoiceData
                                .setSource(c.getSource())
                                .setGuard(c.getGuard())
                                .setActions(c.getActions())
                                .setTarget(c.getTarget());
                        return secChoiceData;
                    }).collect(Collectors.toList());
        }

        public boolean hasInherited(TransitionData<S, E, C> parentTransition) {
            S source = parentTransition.getSource();
            E event = parentTransition.getEvent();
            return hasInherited(source, event);
        }

        public boolean hasInherited(S source, E event) {
            if (childTransitionMap.containsKey(source)) {
                return childTransitionMap.get(source).containsKey(event);
            }
            return false;
        }

        public void inherit() {
            TransitionsData<S, E, C> child = childModel.getTransitionsData();
            List<TransitionData<S, E, C>> childTransitionDataList = child.getTransitionDataList();
            TransitionsData<S, E, C> transitionsData = parentModel.getTransitionsData();
            //inherit transition
            for (TransitionData<S, E, C> secTransitionData : transitionsData.getTransitionDataList()) {
                if (secTransitionData.getKind() == TransitionKind.INTERNAL) {
                    continue;
                }
                if (!hasInherited(secTransitionData)) {
                    childTransitionDataList.add(copyTransition(secTransitionData));
                }
            }
            //exclude single point
            StateData<S, E, C> start = parentModel.getStatesData().getStart();
            Set<S> allTargets = new HashSet<>();
            if (start != null) {
                S startState = start.getState();
                //todo 暂时采用简单的暴力处理办法，否则需要建图处理
                while (true) {
                    allTargets = childTransitionDataList.stream()
                            .filter(t -> t.getKind() == TransitionKind.EXTERNAL)
                            .map(TransitionData::getTarget)
                            .collect(Collectors.toSet());
                    Set<S> finalAllTargets = allTargets;
                    List<TransitionData<S, E, C>> noEntrancePoint = childTransitionDataList.stream()
                            .filter(t -> t.getKind() == TransitionKind.EXTERNAL)
                            .filter(t -> !finalAllTargets.contains(t.getSource()) && t.getSource() != startState)
                            .collect(Collectors.toList());
                    if (noEntrancePoint.isEmpty()) {
                        break;
                    }
                    for (TransitionData<S, E, C> s : noEntrancePoint) {
                        childTransitionDataList.remove(s);
                        allTargets.remove(s.getSource());
                    }
                }
            }
            //inherit choice
            for (Entry<S, List<ChoiceData<S, E, C>>> entry : transitionsData.getChoiceMap().entrySet()) {
                S s = entry.getKey();
                List<ChoiceData<S, E, C>> list = entry.getValue();
                if (allTargets.contains(s) && !childChoicesMap.containsKey(s)) {
                    childChoicesMap.put(s, copyChoice(list));
                }
            }
            //inherit states
            StatesData<S, E, C> statesData = childModel.getStatesData();
            if (statesData.getStart() == null) {
                statesData.setStart(parentModel.getStatesData().getStart());
            }
            for (StateData<S, E, C> choice : parentModel.getStatesData().getChoices()) {
                if (allTargets.contains(choice.getState())) {
                    statesData.getChoices().add(choice);
                }
            }
        }
    }
}
