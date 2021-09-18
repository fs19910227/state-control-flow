package com.doubao.statemachine.extend.support.uml;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import lombok.experimental.UtilityClass;

/**
 * uml generator factory
 *
 * @author doubao
 * @date 2021/5/28
 */
@UtilityClass
public class UmlGeneratorFactory {
	/**
	 * generator map
	 */
	private final Map<String, Supplier<UmlGenerator<?, ?, ?>>> generatorMap = new HashMap<>();

	static {
		registerGenerator(UmlGeneratorType.PLANT_UML.name(), PlantUmlGenerator::new);
	}

	/**
	 * get a uml generator
	 *
	 * @param name generator name
	 * @return generator
	 */
	@SuppressWarnings("unchecked")
	public <S, E, C> UmlGenerator<S, E, C> getGenerator(String name) {
		Supplier<UmlGenerator<?, ?, ?>> umlGeneratorSupplier = generatorMap.get(name);
		UmlGenerator<?, ?, ?> umlGenerator = umlGeneratorSupplier.get();
		return (UmlGenerator<S, E, C>)umlGenerator;
	}

	/**
	 * register a generator
	 *
	 * @param name              name
	 * @param generatorSupplier generator suplier
	 */
	public void registerGenerator(String name, Supplier<UmlGenerator<?, ?, ?>> generatorSupplier) {
		generatorMap.put(name, generatorSupplier);
	}
}
