package com.doubao.statemachine.state;

import java.util.Objects;

import lombok.Data;
import lombok.ToString;

/**
 * Object State implement
 *
 * @author doubao
 * @date 2021/4/28
 */
@ToString
@Data
public class ObjectState<S, E, C> implements State<S, E, C> {
	private final S id;
	private final StateType stateType;
	private boolean isStart;
	private boolean isFinal;
	private String group;

	public ObjectState(S id, StateType type) {
		this.id = id;
		this.stateType = type;
	}

	@Override
	public S getId() {
		return id;
	}

	@Override
	public boolean isStart() {
		return isStart;
	}
	@Override
	public boolean isFinal(){
		return isFinal;
	}

	@Override
	public StateType getType() {
		return stateType;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }
		ObjectState<?, ?, ?> that = (ObjectState<?, ?, ?>)o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
