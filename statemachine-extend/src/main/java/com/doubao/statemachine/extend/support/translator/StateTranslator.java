package com.doubao.statemachine.extend.support.translator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.doubao.statemachine.extend.config.StateMachineConfig;
import com.doubao.statemachine.extend.support.StateDesc;

/**
 * StateTranslator
 *
 * @param <S>
 * @author doubao
 */
public interface StateTranslator<S> {
    /**
     * translator ref machine group
     *
     * @return group default
     */
    default String refMachineGroup() {
        return StateMachineConfig.DEFAULT_GROUP;
    }

    /**
     * translator ref machine group
     *
     * @return null if all
     */
    default String refMachineId() {
        return null;
    }

    /**
     * int value for sort
     *
     * @param stateDesc
     * @return
     */
    default int sort(StateDesc<S> stateDesc) {
        S id = stateDesc.getId();
        if (id instanceof Enum) {
            Enum e = (Enum) id;
            return e.ordinal();
        }
        return 0;
    }

    /**
     * state name for transition
     *
     * @param state
     * @return
     */
    default String transitionName(StateDesc<S> state) {
        return getName(state.getId());
    }

    /**
     * state name
     *
     * @param state
     * @return
     */
    default String getName(S state) {
        return state.toString();
    }

    /**
     * state label
     * for show
     *
     * @param sStateDesc
     * @return
     */
    String getLabel(StateDesc<S> sStateDesc);

    /**
     * comment list
     *
     * @param state
     * @return
     */
    default List<String> getCommentList(StateDesc<S> state) {
        List<String> collect = new ArrayList<>();
        Set<S> refStates = state.getRefStates();
        if (refStates != null && !refStates.isEmpty()) {
            collect.add("refStates:" + refStates);
        }
        if (state.getComments() != null) {
            collect.addAll(state.getComments());
        }
        return collect;
    }

    /**
     * get properties
     *
     * @param state desc
     * @return property map
     */
    default Map<String, String> getProperties(StateDesc<S> state) {
        return Collections.emptyMap();
    }

}