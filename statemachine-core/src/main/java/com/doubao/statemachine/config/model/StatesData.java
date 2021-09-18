package com.doubao.statemachine.config.model;

import java.util.Map;
import java.util.Set;

import com.doubao.statemachine.state.history.HistoryRecorder;
import lombok.Data;

/**
 * @author doubao
 * @date 2021/05/09 13:08
 */
@Data
public class StatesData<S, E, C> {
    /**
     * init state
     */
    private StateData<S, E, C> start;
    /**
     * final state list
     */
    private Set<StateData<S, E, C>> finalSet;
    /**
     * choice state
     */
    private Set<StateData<S, E, C>> choices;
    /**
     * history states
     */
    private Set<StateData<S, E, C>> historyStates;
    /**
     * entry states
     */
    private Set<StateData<S, E, C>> entryStates;
    /**
     * exit states
     */
    private Set<StateData<S, E, C>> exitStates;
    /**
     * history recorder
     */
    private HistoryRecorder<S, E, C> historyRecorder;
    /**
     * state group defines
     */
    private Map<String, Set<S>> stateGroupMap;

}
