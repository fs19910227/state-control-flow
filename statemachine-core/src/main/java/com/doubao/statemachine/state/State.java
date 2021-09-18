package com.doubao.statemachine.state;

/**
 * @author doubao
 * @date 2021/4/28
 */
public interface State<S, E, C> {
	/**
	 * state group
	 * now for show only
	 * @return group
	 */
	default String group(){
		return null;
	}
	/**
	 * get state id
	 *
	 * @return S
	 */
	S getId();

	/**
	 * is start state
 	 * @return t/f
	 */
	boolean isStart();

	/**
	 * is final state
	 * @return t/f
	 */
	boolean isFinal();
	/**
	 * get state type
	 * @return
	 */
	StateType getType();

}
