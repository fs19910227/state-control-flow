package com.doubao.statemachine.extend.support.translator;

import com.doubao.statemachine.extend.support.DefaultStateTranslator;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.groupingBy;

/**
 * StateTranslatorManager
 *
 * @author doubao
 * @date 2021/7/28
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class DefaultStateTranslatorManager implements StateTranslatorManager {

    private final Map<String, List<StateTranslator>> stateTranslatorMap;

    private static final StateTranslator DEFAULT_TRANSLATOR = new DefaultStateTranslator<>(Object::toString);

    public DefaultStateTranslatorManager(Collection<StateTranslator> translators) {
        stateTranslatorMap = translators.stream()
                .collect(groupingBy(StateTranslator::refMachineGroup));
    }


    @Override
    public <S> StateTranslator<S> getTranslator(String group, String machineId) {
        List<StateTranslator> stateTranslators = stateTranslatorMap.get(group);
        if (stateTranslators == null || stateTranslators.isEmpty()) {
            return (StateTranslator<S>) DEFAULT_TRANSLATOR;
        }
        if (machineId == null) {
            return getGroupTranslator(stateTranslators);
        }
        //exact match first
        //then group match
        return stateTranslators.stream()
                .filter(t -> machineId.equals(t.refMachineId()))
                .findFirst()
                .orElseGet(() -> getGroupTranslator(stateTranslators));
    }

    @Override
    public <S> StateTranslator<S> getTranslator(String group) {
        List<StateTranslator> stateTranslators = stateTranslatorMap.get(group);
        return getGroupTranslator(stateTranslators);
    }

    private <S> StateTranslator<S> getGroupTranslator(List<StateTranslator> stateTranslators) {
        if (stateTranslators == null || stateTranslators.isEmpty()) {
            return (StateTranslator<S>) DEFAULT_TRANSLATOR;
        }
        //group match
        Optional<StateTranslator> collect = stateTranslators.stream()
                .filter(t -> t.refMachineId() == null)
                .findFirst();
        return collect.orElseGet(() -> (StateTranslator<S>) DEFAULT_TRANSLATOR);
    }
}
