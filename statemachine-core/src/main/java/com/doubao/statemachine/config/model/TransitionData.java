package com.doubao.statemachine.config.model;

import java.util.List;
import java.util.Objects;

import com.doubao.statemachine.action.Action;
import com.doubao.statemachine.guard.Guard;
import com.doubao.statemachine.transition.TransitionKind;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * model contains transition config data
 *
 * @author doubao
 * @date 2021/04/27 22:58
 */
@Data
@Accessors(chain = true)
public class TransitionData<S, E, C> {
	private S source;
	private S target;
	private E event;
	private Guard<S, E, C> guard;
	private List<Action<S, E, C>> actions;
	private TransitionKind kind;

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }
		TransitionData<?, ?, ?> that = (TransitionData<?, ?, ?>)o;
		return Objects.equals(source, that.source) &&
			Objects.equals(target, that.target) &&
			Objects.equals(event, that.event) &&
			kind == that.kind;
	}

	@Override
	public int hashCode() {
		return Objects.hash(source, target, event, kind);
	}
}
