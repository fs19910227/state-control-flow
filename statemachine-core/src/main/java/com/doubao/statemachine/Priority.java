package com.doubao.statemachine;

import java.util.Comparator;

/**
 * indicate the priority of subclass
 *
 * @author doubao
 * @date 2021/05/04 19:45
 */
public interface Priority {
	int LOWEST_PRIORITY = Integer.MAX_VALUE;
	int HIGHEST_PRIORITY = Integer.MIN_VALUE;

	/**
	 * return priority
	 *
	 * @return
	 */
	default int priority() {
		return 0;
	}

	/**
	 * default comparator
	 *
	 * @return comparator
	 */
	static Comparator<Priority> comparator() {
		return Comparator.comparing(Priority::priority,
			Comparator.naturalOrder());
	}
}
