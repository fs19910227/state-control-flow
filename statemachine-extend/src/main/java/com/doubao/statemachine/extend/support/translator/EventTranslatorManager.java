package com.doubao.statemachine.extend.support.translator;

/**
 * @author doubao
 * @date 2021/7/28
 */
public interface EventTranslatorManager {

    /**
     * get translator
     *
     * @param group
     * @param machineId
     * @param <S>
     * @return
     */
    <S> EventTranslator<S> getTranslator(String group, String machineId);

    /**
     * get translator
     *
     * @param group
     * @param <S>
     * @return
     */
    <S> EventTranslator<S> getTranslator(String group);
}
