package com.doubao.statemachine;

import com.doubao.statemachine.state.State;
import lombok.Getter;
import lombok.Setter;

/**
 * event process result
 *
 * @author doubao
 * @date 2021/4/30
 */
public class Result<S, E, C> {
	/**
	 * return false,when can't find transition or transit error
	 */
	@Getter
	@Setter
	private boolean isSucceed;
	/**
	 * whether transit executed or not
	 * return false when transit not happened (usually caused by a Guard),or not find any transition
	 */
	@Getter
	@Setter
	private boolean transit;
	@Getter
	@Setter
	/**
	 * target state
	 */
	private State<S, E, C> targetState;
	/**
	 * user customize content
	 */
	@Getter
	@Setter
	private C content;

	public static <S, E, C> Result<S, E, C> of(boolean isSucceed, boolean transit, State<S, E, C> target, C content) {
		Result<S, E, C> sResult = new Result<>();
		sResult.setSucceed(isSucceed);
		sResult.setTargetState(target);
		sResult.setContent(content);
		sResult.setTransit(transit);
		return sResult;
	}
}
