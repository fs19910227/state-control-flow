package com.doubao.statemachine.config.model;

import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * model contains transitions config data
 *
 * @author doubao
 * @date 2021/04/27 22:58
 */
@Data
@Accessors(chain = true)
public class TransitionsData<S, E, C> {
	private List<TransitionData<S, E, C>> transitionDataList;
	private Map<S, List<ChoiceData<S, E, C>>> choiceMap;

}
