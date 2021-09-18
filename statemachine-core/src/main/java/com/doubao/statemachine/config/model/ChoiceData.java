package com.doubao.statemachine.config.model;

import java.util.List;

import com.doubao.statemachine.action.Action;
import com.doubao.statemachine.guard.Guard;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * model contains choice transition config data
 *
 * @author doubao
 * @date 2021/04/27 22:58
 */
@Data
@Accessors(chain = true)
public class ChoiceData<S, E, C> {
	private S source;
	private S target;
	private Guard<S, E, C> guard;
	private List<Action<S, E, C>> actions;
}
