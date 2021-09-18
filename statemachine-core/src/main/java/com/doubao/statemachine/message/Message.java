package com.doubao.statemachine.message;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * message contains generic information
 *
 * @author doubao
 * @date 2021/4/29
 */
@Data
@Accessors(chain = true)
public class Message<S, E, C> {
	private Message() {
	}

	public static <S, E, C> Message<S, E, C> of(S source, E event) {
		return new Message<S, E, C>().setSource(source).setEvent(event);
	}

	public static <S, E, C> Message<S, E, C> of(S source, E event, C content) {
		return new Message<S, E, C>()
			.setSource(source)
			.setEvent(event)
			.setContent(content);
	}

	public Message<S, E, C> addExtra(String extraKey, Object extraObj) {
		if (extraMap == null) {
			extraMap = new HashMap<>(2);
		}
		extraMap.put(extraKey, extraObj);
		return this;
	}

	/**
	 * extraMap
	 * can take extraMessage
	 */
	private Map<String, Object> extraMap;
	private S source;
	private E event;
	private C content;

}
