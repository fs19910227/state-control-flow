package com.doubao.statemachine.extend.support.translator;

import com.doubao.statemachine.extend.support.DefaultEventTranslator;

import java.util.*;

import static java.util.stream.Collectors.groupingBy;

/**
 * EventTranslatorManager
 *
 * @author doubao
 * @date 2021/7/28
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class DefaultEventTranslatorManager implements EventTranslatorManager {

    private final Map<String, List<EventTranslator>> translatorMap;

    private static final EventTranslator DEFAULT_TRANSLATOR = new DefaultEventTranslator<>(Object::toString);

    @SuppressWarnings("rawuse")
    public DefaultEventTranslatorManager(Collection<EventTranslator> eventTranslators) {
        translatorMap = eventTranslators.stream()
                .collect(groupingBy(EventTranslator::refMachineGroup));

    }

    @Override
    public <S> EventTranslator<S> getTranslator(String group, String machineId) {
        List<EventTranslator> translators = translatorMap.get(group);
        if (translators == null || translators.isEmpty()) {
            return (EventTranslator<S>) DEFAULT_TRANSLATOR;
        }
        if (machineId == null) {
            return getGroupTranslator(translators);
        }
        //exact match first
        //then group match
        return translators.stream()
                .filter(t -> machineId.equals(t.refMachineId()))
                .findFirst()
                .orElseGet(() -> getGroupTranslator(translators));
    }

    @Override
    public <S> EventTranslator<S> getTranslator(String group) {
        List<EventTranslator> translators = translatorMap.get(group);
        return getGroupTranslator(translators);
    }

    private <S> EventTranslator<S> getGroupTranslator(List<EventTranslator> translators) {
        if (translators == null || translators.isEmpty()) {
            return (EventTranslator<S>) DEFAULT_TRANSLATOR;
        }
        //group match
        Optional<EventTranslator> collect = translators.stream()
                .filter(t -> t.refMachineId() == null)
                .findFirst();
        return collect.orElseGet(() -> (EventTranslator<S>) DEFAULT_TRANSLATOR);
    }
}
