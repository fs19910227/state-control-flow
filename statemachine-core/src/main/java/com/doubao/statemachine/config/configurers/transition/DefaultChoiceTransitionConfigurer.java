package com.doubao.statemachine.config.configurers.transition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.doubao.statemachine.config.builders.StateMachineTransitionBuilder;
import com.doubao.statemachine.config.configurers.ConfigurerAdapter;
import com.doubao.statemachine.config.model.ChoiceData;
import com.doubao.statemachine.config.model.TransitionsData;
import com.doubao.statemachine.action.Action;
import com.doubao.statemachine.guard.Guard;

/**
 * default ChoiceTransitionConfigurer
 *
 * @author doubao
 * @date 2021/04/27 22:18
 */
public class DefaultChoiceTransitionConfigurer<S, E, C>
	extends ConfigurerAdapter<TransitionsData<S, E, C>, StateMachineTransitionBuilder<S, E, C>>
	implements ChoiceTransitionConfigurer<S, E, C> {
	private S source;
	private ChoiceData<S, E, C> first;
	private final List<ChoiceData<S, E, C>> thens = new ArrayList<>();
	private ChoiceData<S, E, C> last;

	@Override
	public void configure(StateMachineTransitionBuilder<S, E, C> builder) {
		List<ChoiceData<S, E, C>> choices = new ArrayList<>();
		if (first != null) {
			choices.add(first.setSource(source));
		}
		for (ChoiceData<S, E, C> then : thens) {
			then.setSource(source);
		}
		choices.addAll(thens);
		if (last != null) {
			choices.add(last.setSource(source));
		}
		builder.addChoice(source, choices);
	}

	@Override
	public ChoiceTransitionConfigurer<S, E, C> source(S source) {
		this.source = source;
		return this;
	}

	@Override
	public ChoiceTransitionConfigurer<S, E, C> first(Guard<S, E, C> guard, Action<S, E, C> action) {
		return first(null, guard, Collections.singletonList(action));
	}

	@Override
	public ChoiceTransitionConfigurer<S, E, C> first(S target, Guard<S, E, C> guard) {
		return first(target, guard, Collections.emptyList());
	}

	@Override
	public ChoiceTransitionConfigurer<S, E, C> first(S target, Guard<S, E, C> guard, Action<S, E, C> action) {
		return first(target, guard, Collections.singletonList(action));
	}

	@Override
	public ChoiceTransitionConfigurer<S, E, C> first(S target, Guard<S, E, C> guard, List<Action<S, E, C>> actions) {
		first = new ChoiceData<>();
		first.setTarget(target)
			.setGuard(guard);
		if (actions != null) {
			first.setActions(actions);
		}
		return this;
	}

	@Override
	public ChoiceTransitionConfigurer<S, E, C> then(S target, Guard<S, E, C> guard) {
		return then(target, guard, Collections.emptyList());
	}

	@Override
	public ChoiceTransitionConfigurer<S, E, C> then(S target, Guard<S, E, C> guard, Action<S, E, C> action) {
		return then(target, guard, Collections.singletonList(action));
	}

	@Override
	public ChoiceTransitionConfigurer<S, E, C> then(S target, Guard<S, E, C> guard, List<Action<S, E, C>> actions) {
		ChoiceData<S, E, C> then = new ChoiceData<>();
		then.setTarget(target)
			.setGuard(guard);
		if (actions != null) {
			then.setActions(actions);
		}
		thens.add(then);
		return this;
	}

	@Override
	public ChoiceTransitionConfigurer<S, E, C> last(S target) {
		return last(target, Collections.emptyList());
	}

	@Override
	public ChoiceTransitionConfigurer<S, E, C> last(S target, Action<S, E, C> action) {
		return last(target, Collections.singletonList(action));
	}

	@Override
	public ChoiceTransitionConfigurer<S, E, C> last(S target, List<Action<S, E, C>> actions) {
		last = new ChoiceData<>();
		last.setTarget(target);
		if (actions != null) {
			last.setActions(actions);
		}
		return this;
	}
}
