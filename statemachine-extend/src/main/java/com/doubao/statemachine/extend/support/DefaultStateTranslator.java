package com.doubao.statemachine.extend.support;

import com.doubao.statemachine.extend.support.translator.StateTranslator;

import java.util.function.Function;

/**
 * state translator
 *
 * @param <S>
 * @author doubao
 */
public class DefaultStateTranslator<S> implements StateTranslator<S> {
    private final Function<S, String> labelTranslator;

    public DefaultStateTranslator(Function<S, String> stateLabelTranslator) {
        this.labelTranslator = stateLabelTranslator;
    }

    @Override
    public String getLabel(StateDesc<S> state) {
        return labelTranslator.apply(state.getId());
    }

}