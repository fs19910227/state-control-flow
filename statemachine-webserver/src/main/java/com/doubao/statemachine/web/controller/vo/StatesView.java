package com.doubao.statemachine.web.controller.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.doubao.statemachine.extend.support.StateDesc;
import lombok.Data;

/**
 * states view
 *
 * @author doubao
 * @date 2021/05/29 20:57
 */
@Data
public class StatesView {
	@Data
	public static class HeadMeta {
		public HeadMeta(String label, String propKey) {
			this.label = label;
			this.propKey = propKey;
		}

		private String label;
		private String propKey;
	}

	private List<StateDesc<?>> states;
	/**
	 * 表头
	 */
	private List<HeadMeta> heads;

	public StatesView(List<StateDesc<?>> states) {
		this.states = states;
		this.heads = new ArrayList<>();
		this.heads.add(new HeadMeta("name", "name"));
		this.heads.add(new HeadMeta("label", "label"));
		//
		states.stream()
			.map(StateDesc::getProperties)
			.filter(Objects::nonNull)
			.flatMap(p -> p.keySet().stream())
			.distinct()
			.forEach(key -> {
				this.heads.add(new HeadMeta(key, "properties." + key));
			});
		//
		this.heads.add(new HeadMeta("comments", "comments"));
	}

}
