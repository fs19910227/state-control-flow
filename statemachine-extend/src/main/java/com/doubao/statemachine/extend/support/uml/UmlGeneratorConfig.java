package com.doubao.statemachine.extend.support.uml;

import com.doubao.statemachine.extend.support.translator.EventTranslator;
import com.doubao.statemachine.config.model.StateMachineModel;
import com.doubao.statemachine.extend.support.translator.StateTranslator;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * uml generator config
 *
 * @author doubao
 * @date 2021/06/01
 */
@Data
@Accessors(chain = true)
public class UmlGeneratorConfig<S, E, C> {
	/**
	 * eventViewMode
	 */
	private UmlGeneratorConfigProperties properties;
	/**
	 * stateTranslator
	 */
	private StateTranslator<S> stateTranslator;
	/**
	 * eventTranslator
	 */
	private EventTranslator<E> eventTranslator;
	/**
	 * machine model
	 */
	private StateMachineModel<S, E, C> machineModel;

	public UmlGeneratorConfig(StateTranslator<S> stateTranslator,
		EventTranslator<E> eventTranslator, StateMachineModel<S, E, C> machineModel) {
		this.stateTranslator = stateTranslator;
		this.eventTranslator = eventTranslator;
		this.machineModel = machineModel;
		this.properties = new UmlGeneratorConfigProperties();
	}
}