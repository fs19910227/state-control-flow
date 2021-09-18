package com.doubao.statemachine.config.configurers.state;

import java.util.Set;

import com.doubao.statemachine.state.history.HistoryRecorder;


/**
 * @author doubao
 * @date 2021/05/09 13:05
 */
public interface StateConfigurer<S, E, C> {
    /**
     * define start state
     *
     * @param state state
     * @return this
     */
    StateConfigurer<S, E, C> startState(S state);

    /**
     * define final state
     *
     * @param state state
     * @return
     */
    StateConfigurer<S, E, C> finalState(S state);

    /**
     * define state group
     *
     * @param group group name
     * @param state state set
     * @return this
     */
    StateConfigurer<S, E, C> stateGroup( String group, S... state);

    /**
     * define choice state
     *
     * @param state state
     * @return this
     */
    StateConfigurer<S, E, C> choiceState(S state);

    /**
     * define entrance state
     *
     * @param state entry
     * @return this
     */
    StateConfigurer<S, E, C> entrance(S state);

    /**
     * define exit state
     *
     * @param state exit
     * @return this
     */
    StateConfigurer<S, E, C> exit(S state);

    /**
     * define history state
     *
     * @param state     state
     * @param refStates states be recorded
     * @return this
     */
    StateConfigurer<S, E, C> historyState(S state, Set<S> refStates);

    /**
     * history recorder
     *
     * @param historyRecorder recorder to save and get history
     * @return this
     */
    StateConfigurer<S, E, C> historyRecorder(HistoryRecorder<S, E, C> historyRecorder);
}
