package com.doubao.statemachine.extend.support.translator;

/**
 * @author doubao
 * @date 2021/7/28
 */
public interface StateTranslatorManager {

    /**
     * get translator
     *
     * @param group
     * @param machineId
     * @param <S>
     * @return
     */
    <S> StateTranslator<S> getTranslator(String group, String machineId);

    /**
     * get translator
     *
     * @param group
     * @param <S>
     * @return
     */
    <S> StateTranslator<S> getTranslator(String group);
}
