package com.doubao.statemachine.config.builders;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.doubao.statemachine.config.configurers.state.DefaultStateConfigurer;
import com.doubao.statemachine.config.configurers.state.StateConfigurer;
import com.doubao.statemachine.config.model.StateData;
import com.doubao.statemachine.state.StateType;
import com.doubao.statemachine.config.model.StatesData;
import com.doubao.statemachine.state.history.HistoryRecorder;

/**
 * StateMachine Transition Builder
 *
 * @author doubao
 * @date 2021/04/27 21:20
 */
public class StateMachineStateBuilder<S, E, C>
        extends Builder<StatesData<S, E, C>>
        implements StateMachineStateConfigurer<S, E, C> {
    private StateData<S, E, C> start;
    private Set<StateData<S, E, C>> finalSet;
    private final Set<StateData<S, E, C>> choices = new HashSet<>();
    private final Set<StateData<S, E, C>> histories = new HashSet<>();
    private final Set<StateData<S, E, C>> entries = new HashSet<>();
    private final Set<StateData<S, E, C>> exits = new HashSet<>();
    private final List<StateData<S, E, C>> normalStateDataList = new ArrayList<>();
    private Map<String, Set<S>> stateGroupMap = new HashMap<>();
    private HistoryRecorder<S, E, C> historyRecorder;

    @Override
    public StatesData<S, E, C> build() {
        super.doConfig();
        StatesData<S, E, C> statesData = new StatesData<>();
        statesData.setFinalSet(finalSet);
        statesData.setStart(start);
        statesData.setChoices(choices);
        statesData.setEntryStates(entries);
        statesData.setExitStates(exits);
        statesData.setHistoryStates(histories);
        statesData.setHistoryRecorder(historyRecorder);
        statesData.setStateGroupMap(stateGroupMap);
        return statesData;
    }

    @Override
    public StateConfigurer<S, E, C> withStates() {
        return apply(new DefaultStateConfigurer<>());
    }

    public void setChoices(Set<S> choices) {
        for (S choice : choices) {
            this.choices.add(new StateData<S, E, C>()
                    .setType(StateType.CHOICE)
                    .setState(choice));
        }
    }

    public void setStart(S state) {
        this.start = new StateData<S, E, C>()
                .setStart(true)
                .setState(state);
    }

    public void setFinalSet(Set<S> stateSet) {
        this.finalSet = stateSet.stream()
                .map(s -> new StateData<S, E, C>()
                        .setFinal(true)
                        .setState(s))
                .collect(Collectors.toSet());
    }

    public void setEntries(Set<S> entries) {
        for (S entry : entries) {
            StateData<S, E, C> secStateData = new StateData<>();
            secStateData.setState(entry);
            secStateData.setType(StateType.ENTRANCE);
            this.entries.add(secStateData);
        }
    }

    public void setExits(Set<S> exits) {
        for (S exit : exits) {
            StateData<S, E, C> secStateData = new StateData<>();
            secStateData.setState(exit);
            secStateData.setType(StateType.EXIT);
            this.exits.add(secStateData);
        }
    }

    public void setStateGroup(Map<String, Set<S>> stateGroupMap) {
        this.stateGroupMap = stateGroupMap;
    }

    public void setHistories(Map<S, Set<S>> histories) {
        for (Entry<S, Set<S>> entry : histories.entrySet()) {
            S key = entry.getKey();
            Set<S> value = entry.getValue();
            StateData<S, E, C> secStateData = new StateData<>();
            secStateData.setState(key);
            secStateData.setType(StateType.HISTORY);
            secStateData.setRefStates(value);
            this.histories.add(secStateData);
        }
    }

    public void setHistoryRecorder(HistoryRecorder<S, E, C> historyRecorder) {
        this.historyRecorder = historyRecorder;
    }
}
