package com.doubao.statemachine.extend.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.doubao.statemachine.extend.support.translator.EventTranslator;
import lombok.Data;

/**
 * event desc
 *
 * @param <E>
 * @author doubao
 */
@Data
public class EventDesc<E> {
	private final int sort;
	private E id;
	private String machineId;
	private String name;
	private String label;
	private List<String> comments;

	public EventDesc<E> appendComment(String comment) {
		if (comments == null) {
			comments = new ArrayList<>();
		}
		comments.add(comment);
		return this;
	}

	public EventDesc(String machineId, E event, EventTranslator<E> translator) {
		this.machineId = machineId;
		this.id = event;
		this.name = translator.getName(event);
		this.label = translator.getLabel(event);
		this.comments = translator.getComments(this);
		this.sort = translator.sort(this);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (!(o instanceof EventDesc)) { return false; }
		EventDesc<?> eventDesc = (EventDesc<?>)o;
		return Objects.equals(id, eventDesc.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}