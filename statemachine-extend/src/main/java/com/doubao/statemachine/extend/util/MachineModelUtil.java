package com.doubao.statemachine.extend.util;

import java.util.*;
import java.util.stream.Collectors;

import com.doubao.statemachine.extend.support.*;
import com.doubao.statemachine.extend.support.translator.EventTranslator;
import com.doubao.statemachine.action.Action;
import com.doubao.statemachine.config.model.ChoiceData;
import com.doubao.statemachine.config.model.ConfigurationData;
import com.doubao.statemachine.config.model.StateData;
import com.doubao.statemachine.config.model.StateMachineModel;
import com.doubao.statemachine.config.model.StatesData;
import com.doubao.statemachine.config.model.TransitionData;
import com.doubao.statemachine.config.model.TransitionsData;
import com.doubao.statemachine.core.StateMachineModelParser;
import com.doubao.statemachine.extend.support.translator.StateTranslator;
import com.doubao.statemachine.guard.Guard;
import lombok.experimental.UtilityClass;

import static java.util.stream.Collectors.toMap;

/**
 * MachineModelUtil
 *
 * @author doubao
 * @date 2021/5/25
 */
@UtilityClass
public class MachineModelUtil {
    /**
     * merge model state data and transition data
     * generate all state desc
     *
     * @param model           stateMachineModel
     * @param stateTranslator stateTranslator
     * @param <S>             State
     * @param <E>             Event
     * @param <C>             Content
     * @return
     */
    public <S, E, C> Map<S, StateDesc<S>> mergeStateData(StateMachineModel<S, E, C> model,
                                                         StateTranslator<S> stateTranslator) {
        ConfigurationData<S, E, C> configurationData = model.getConfigurationData();
        String machineId = configurationData.getMachineId();
        StatesData<S, E, C> statesData = model.getStatesData();
        //build from transition
        Set<StateData<S, E, C>> statesFromTransition = StateMachineModelParser.resolveStatesFromTransition(
                model.getTransitionsData());
        Map<S, StateDesc<S>> collect = statesFromTransition
                .stream()
                .map(d -> new StateDesc<>(machineId, d, stateTranslator))
                .collect(toMap(StateDesc::getId, s -> s));
        //build from choice
        for (StateData<S, E, C> choice : statesData.getChoices()) {
            collect.put(choice.getState(), new StateDesc<>(machineId, choice, stateTranslator));
        }
        //build from history
        for (StateData<S, E, C> history : statesData.getHistoryStates()) {
            collect.put(history.getState(), new StateDesc<>(machineId, history, stateTranslator));
            for (S refState : history.getRefStates()) {
                collect.get(refState).appendComment("History By " + history.getState());
            }
        }
        //build from entrance
        for (StateData<S, E, C> entry : statesData.getEntryStates()) {
            collect.put(entry.getState(), new StateDesc<>(machineId, entry, stateTranslator));
        }
        //build from exit
        for (StateData<S, E, C> exit : statesData.getExitStates()) {
            collect.put(exit.getState(), new StateDesc<>(machineId, exit, stateTranslator));
        }
        //fill group
        Map<String, Set<S>> stateGroupMap = statesData.getStateGroupMap();
        for (Map.Entry<String, Set<S>> entry : stateGroupMap.entrySet()) {
            String group = entry.getKey();
            Set<S> statues = entry.getValue();
            for (S statue : statues) {
                collect.get(statue).setGroup(group);
            }
        }
        //fill start
        StateData<S, E, C> start = statesData.getStart();
        if (start != null) {
            collect.get(start.getState()).setStart(true);
        }
        //fill final
        Set<StateData<S, E, C>> finalSet = statesData.getFinalSet();
        if (finalSet != null) {
            for (StateData<S, E, C> end : finalSet) {
                collect.get(end.getState()).setFinal(true);
            }
        }
        return collect;
    }

    /**
     * merge event data
     *
     * @param model      model
     * @param translator translator
     * @param <S>        S
     * @param <E>        E
     * @param <C>        C
     * @return map events
     */
    public <S, E, C> Map<E, EventDesc<E>> mergeEventData(StateMachineModel<S, E, C> model,
                                                         EventTranslator<E> translator) {
        ConfigurationData<S, E, C> configurationData = model.getConfigurationData();
        String machineId = configurationData.getMachineId();
        Set<E> events = StateMachineModelParser.resolveEventsFromTransition(
                model.getTransitionsData());
        return events
                .stream()
                .filter(Objects::nonNull)
                .map(d -> new EventDesc<>(machineId, d, translator))
                .collect(toMap(EventDesc::getId, s -> s));
    }

    /**
     * merge guards data
     *
     * @param <S>   S
     * @param <E>   E
     * @param <C>   C
     * @param model model
     * @return guards
     */
    public <S, E, C> Map<Class<? extends Guard>, GuardDesc> mergeGuardData(StateMachineModel<S, E, C> model) {
        //prepare data
        TransitionsData<S, E, C> transitionsData = model.getTransitionsData();
        List<TransitionData<S, E, C>> transitionDataList = transitionsData.getTransitionDataList();
        Map<S, List<ChoiceData<S, E, C>>> choiceMap = transitionsData.getChoiceMap();
        //append guards meta info
        Map<Class<? extends Guard>, Guard<S, E, C>> guardMap = new HashMap<>();
        transitionDataList.stream()
                .filter(t -> t.getGuard() != null)
                .map(TransitionData::getGuard)
                .forEach(g -> guardMap.put(g.getClass(), g));

        choiceMap.values().stream()
                .flatMap(Collection::stream)
                .filter(c -> c.getGuard() != null)
                .map(ChoiceData::getGuard)
                .forEach(g -> guardMap.put(g.getClass(), g));
        return guardMap.values().stream()
                .collect(toMap(Guard<S, E, C>::getClass, GuardDesc::new));
    }

    /**
     * merge actions data
     *
     * @param <S>   S
     * @param <E>   E
     * @param <C>   C
     * @param model model
     * @return actions
     */
    public <S, E, C> Map<Class<? extends Action>, ActionDesc> mergeActionData(StateMachineModel<S, E, C> model) {
        //prepare data
        TransitionsData<S, E, C> transitionsData = model.getTransitionsData();
        List<TransitionData<S, E, C>> transitionDataList = transitionsData.getTransitionDataList();
        Map<S, List<ChoiceData<S, E, C>>> choiceMap = transitionsData.getChoiceMap();
        //append actions meta info
        Map<Class<? extends Action>, Action<S, E, C>> actionMap = new HashMap<>();
        transitionDataList.stream()
                .filter(t -> t.getActions() != null)
                .map(TransitionData::getActions)
                .flatMap(Collection::stream)
                .forEach(g -> actionMap.put(g.getClass(), g));
        choiceMap.values().stream()
                .flatMap(Collection::stream)
                .filter(c -> c.getActions() != null)
                .map(ChoiceData::getActions)
                .flatMap(Collection::stream)
                .forEach(g -> actionMap.put(g.getClass(), g));
        return actionMap.values().stream()
                .collect(Collectors.toMap(Action<S, E, C>::getClass, ActionDesc::new));
    }

    /**
     * merge actions data
     *
     * @param <S>   S
     * @param <E>   E
     * @param <C>   C
     * @param model model
     * @return actions
     */
    public <S, E, C> List<TransitionDesc<S, E, C>> mergeTransitionData(StateMachineModel<S, E, C> model,
                                                                       Map<S, StateDesc<S>> stateDescMap,
                                                                       Map<E, EventDesc<E>> eventDescMap,
                                                                       Map<Class<? extends Guard>, GuardDesc> guardMap,
                                                                       Map<Class<? extends Action>, ActionDesc> actionDescMap
    ) {
        //prepare data
        TransitionsData<S, E, C> transitionsData = model.getTransitionsData();
        List<TransitionData<S, E, C>> transitionDataList = transitionsData.getTransitionDataList();
        //base transit
        List<TransitionDesc<S, E, C>> baseTransitions = transitionDataList.stream()
                .map(x -> new TransitionDesc<>(x, stateDescMap, eventDescMap, guardMap, actionDescMap))
                .collect(Collectors.toList());
        //choice to transition desc
        List<TransitionDesc<S, E, C>> transitionFromChoice = transitionsData.getChoiceMap()
                .values().stream()
                .flatMap(Collection::stream)
                .map(x -> new TransitionDesc<>(x, stateDescMap, guardMap, actionDescMap))
                .collect(Collectors.toList());
        baseTransitions.addAll(transitionFromChoice);
        return baseTransitions;
    }
}
