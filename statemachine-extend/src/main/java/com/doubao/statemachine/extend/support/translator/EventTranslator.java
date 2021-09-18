package com.doubao.statemachine.extend.support.translator;

import com.doubao.statemachine.extend.config.StateMachineConfig;
import com.doubao.statemachine.extend.support.EventDesc;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * EventTranslator
 *
 * @param <E>
 * @author doubao
 */
public interface EventTranslator<E> {
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
     * @param event
     * @return
     */
    default int sort(EventDesc<E> event) {
        E id = event.getId();
        if (id instanceof Enum) {
            Enum<?> e = (Enum<?>) id;
            return e.ordinal();
        }
        return 0;
    }


    /**
     * get event name
     * from id
     *
     * @param id
     * @return
     */
    default String getName(E id) {
        return id == null ? null : id.toString();
    }


    /**
     * get label
     * for show name
     *
     * @param event
     * @return
     */
    String getLabel(E event);

    /**
     * comment list
     *
     * @param event
     * @return
     */
    default List<String> getComments(EventDesc<E> event) {
        return event.getComments();
    }

    /**
     * comment list
     *
     * @param event
     * @return
     */
    default Map<String, String> getProperties(EventDesc<E> event) {
        return Collections.emptyMap();
    }
}