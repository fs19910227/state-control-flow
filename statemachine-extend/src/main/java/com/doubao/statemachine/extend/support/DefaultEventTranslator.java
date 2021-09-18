package com.doubao.statemachine.extend.support;

import com.doubao.statemachine.extend.support.translator.EventTranslator;

import java.util.function.Function;

/**
 * event translator
 *
 * @param <E>
 * @author doubao
 */
public class DefaultEventTranslator<E> implements EventTranslator<E> {
    private final Function<E, String> labelTranslator;

    public DefaultEventTranslator(Function<E, String> labelTranslator) {
        this.labelTranslator = labelTranslator;
    }


    @Override
    public String getLabel(E event) {
        return labelTranslator.apply(event);
    }
}