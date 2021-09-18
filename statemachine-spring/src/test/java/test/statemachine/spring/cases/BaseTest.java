package test.statemachine.spring.cases;

import java.io.File;
import java.io.FileWriter;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.doubao.statemachine.core.StateMachine;
import com.doubao.statemachine.extend.config.StateMachineConfig;
import com.doubao.statemachine.extend.config.StateMachineGlobalConfig;
import com.doubao.statemachine.extend.manager.DefaultStateMachineManagerFactory;
import com.doubao.statemachine.extend.manager.StateMachineManager;
import com.doubao.statemachine.extend.manager.StateMachineManagerFactory;
import com.doubao.statemachine.extend.support.DefaultEventTranslator;
import com.doubao.statemachine.extend.support.DefaultStateTranslator;
import com.doubao.statemachine.extend.support.uml.PlantUmlGenerator;
import com.doubao.statemachine.extend.support.uml.UmlGenerator;
import com.doubao.statemachine.extend.support.uml.UmlGeneratorConfig;
import com.doubao.statemachine.extend.support.uml.UmlGeneratorFactory;
import com.doubao.statemachine.extend.support.uml.UmlGeneratorType;
import com.doubao.statemachine.spring.annotation.StateMachineScan;
import lombok.SneakyThrows;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import test.statemachine.spring.support.config.Events;
import test.statemachine.spring.support.config.States;
import test.statemachine.spring.support.model.TestOrderInfo;

/**
 * @author doubao
 * @date 2021/5/12
 */
public abstract class BaseTest {
	protected static String umlPath = "src\\test\\resources\\umls";
	@Autowired
	protected StateMachineManager machineManager;

	static {
		UmlGeneratorFactory.registerGenerator(UmlGeneratorType.PLANT_UML.name(), PlantUmlGenerator::new);
	}

	@Configuration
	@StateMachineScan("test.statemachine.spring.support.configs")
	@ComponentScan("test.statemachine.spring")
	public static class Config {
//		@Bean
//		public StateMachineGlobalConfig stateMachineGlobalConfig() {
//			StateMachineGlobalConfig stateMachineGlobalConfig = new StateMachineGlobalConfig();
//			stateMachineGlobalConfig.setGlobalListeners(
//				Lists.newArrayList(new AutoSaveListener(), new ConsoleLogListener()));
//			return stateMachineGlobalConfig;
//		}

		@Bean
		@SuppressWarnings({"rawtypes"})
		public StateMachineManagerFactory stateMachineFactory(ListableBeanFactory beanFactory) {
			DefaultStateMachineManagerFactory factory = new DefaultStateMachineManagerFactory();
			Map<String, StateMachineConfig> configs = beanFactory.getBeansOfType(StateMachineConfig.class);
			Map<String, StateMachineGlobalConfig> beansOfType = beanFactory.getBeansOfType(
				StateMachineGlobalConfig.class);
			Map<String, StateMachineGlobalConfig> globalConfigMap = beansOfType.entrySet().stream()
				.collect(Collectors.toMap(e -> e.getValue().getGroup(), Entry::getValue));
			Collection<StateMachineConfig> values = configs.values();
			factory.init(values, globalConfigMap);
			return factory;
		}

		@Bean
		public StateMachineManager stateMachineManager(
			StateMachineManagerFactory factory) {
			return factory.getStateMachineManager(StateMachineConfig.DEFAULT_GROUP);
		}
	}

	protected StateMachine<States, Events, TestOrderInfo> getById(String machineId) {
		return machineManager.getById(machineId);
	}

	protected StateMachine<States, Events, TestOrderInfo> selectByContent(TestOrderInfo orderInfo) {
		return machineManager.selectOneByContent(orderInfo);
	}

	protected String umlString(StateMachine<States, Events, TestOrderInfo> stateMachine) {
		UmlGenerator<States, Events, TestOrderInfo> generator = UmlGeneratorFactory.getGenerator(
			UmlGeneratorType.PLANT_UML.name());
		UmlGeneratorConfig<States, Events, TestOrderInfo> generatorConfig = new UmlGeneratorConfig<>(
			new DefaultStateTranslator<>(States::getDesc),
			new DefaultEventTranslator<>(Events::getDesc),
			stateMachine.getModel());
		UmlGenerator<States, Events, TestOrderInfo> umlGenerator = generator.init(
			generatorConfig);
		return umlGenerator.generateUmlString();
	}

	protected void printUml(StateMachine<States, Events, TestOrderInfo> stateMachine) {
		System.out.println(umlString(stateMachine));
	}

	@SneakyThrows
	protected void outputUml(StateMachine<States, Events, TestOrderInfo> stateMachine, String fileName) {
		String x = umlString(stateMachine);
		File path = new File(umlPath);
		File file = new File(path, fileName + ".puml");
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(x);
		fileWriter.close();
	}
}
