package com.doubao.statemachine.extend.support.uml;

/**
 * uml generator
 *
 * @author doubao
 * @date 2021/4/29
 */
public interface UmlGenerator<S, E, C> {
	/**
	 * init uml generator
	 *
	 * @param generatorConfig generator config
	 * @return this
	 */
	UmlGenerator<S, E, C> init(UmlGeneratorConfig<S, E, C> generatorConfig);

	/**
	 * generate uml string data
	 *
	 * @return
	 */
	String generateUmlString();
}
