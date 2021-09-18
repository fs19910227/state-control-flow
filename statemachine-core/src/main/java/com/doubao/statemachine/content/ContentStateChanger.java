package com.doubao.statemachine.content;

/**
 * ContentStateChanger
 * use to automatic change content state after state change
 *
 * @author doubao
 * @date 2021/4/29
 */
public interface ContentStateChanger<S,C> {
	/**
	 * will be called when state changed
	 *
	 * @param currentState currentState
	 * @param content      content
	 */
	void onChange(S currentState, C content);
}
