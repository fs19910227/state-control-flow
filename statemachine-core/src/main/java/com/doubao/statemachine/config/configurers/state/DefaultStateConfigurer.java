package com.doubao.statemachine.config.configurers.state;

import com.doubao.statemachine.config.configurers.ConfigurerAdapter;
import com.doubao.statemachine.config.builders.StateMachineStateBuilder;
import com.doubao.statemachine.config.model.StatesData;
import com.doubao.statemachine.state.history.HistoryRecorder;

import java.util.*;

/**
 * DefaultStateConfigurer
 * config special state
 *
 * @author doubao
 * @date 2021/05/09 13:07
 */
public class DefaultStateConfigurer<S, E, C>
        extends ConfigurerAdapter<StatesData<S, E, C>, StateMachineStateBuilder<S, E, C>>
        implements StateConfigurer<S, E, C> {
    /**
     * start state
     */
    private S start;
    /**
     * final states
     */
    private final Set<S> endSet = new HashSet<>();
    /**
     * state group map
     */
    private final Map<String, Set<S>> stateGroupMap = new HashMap<>();
    /**
     * choice point set
     */
    private final Set<S> choices = new HashSet<>();
    /**
     * history point map
     */
    private final Map<S, Set<S>> histories = new HashMap<>();
    /**
     * entry point set
     */
    private final Set<S> entries = new HashSet<>();
    /**
     * exit point set
     */
    private final Set<S> exits = new HashSet<>();
    /**
     * history recorder
     */
    private HistoryRecorder<S, E, C> historyRecorder;

    @Override
    public StateConfigurer<S, E, C> startState(S state) {
        this.start = state;
        return this;
    }

    @Override
    public StateConfigurer<S, E, C> finalState(S state) {
        endSet.add(state);
        return this;
    }

    @Override
    public StateConfigurer<S, E, C> stateGroup( String group, S... state) {
        Set<S> statusSet = new HashSet<>(Arrays.asList(state));
        stateGroupMap.put(group, statusSet);
        return this;
    }

    @Override
    public StateConfigurer<S, E, C> choiceState(S state) {
        choices.add(state);
        return this;
    }

    @Override
    public StateConfigurer<S, E, C> entrance(S state) {
        entries.add(state);
        return this;
    }

    @Override
    public StateConfigurer<S, E, C> exit(S state) {
        exits.add(state);
        return this;
    }

    @Override
    public StateConfigurer<S, E, C> historyState(S state, Set<S> refStates) {
        histories.put(state, refStates);
        return this;
    }

    @Override
    public StateConfigurer<S, E, C> historyRecorder(HistoryRecorder<S, E, C> historyRecorder) {
        this.historyRecorder = historyRecorder;
        return this;
    }

    @Override
    public void configure(StateMachineStateBuilder<S, E, C> builder) {
        if (start != null) {
            builder.setStart(start);
        }
        builder.setFinalSet(endSet);
        builder.setStateGroup(stateGroupMap);
        builder.setChoices(choices);
        builder.setEntries(entries);
        builder.setExits(exits);
        builder.setHistories(histories);
        builder.setHistoryRecorder(historyRecorder);
    }

}
