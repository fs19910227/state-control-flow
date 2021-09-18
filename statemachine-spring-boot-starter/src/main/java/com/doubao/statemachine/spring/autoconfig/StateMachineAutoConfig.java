package com.doubao.statemachine.spring.autoconfig;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.doubao.statemachine.extend.config.StateMachineConfig;
import com.doubao.statemachine.extend.config.StateMachineGlobalConfig;
import com.doubao.statemachine.extend.manager.DefaultGenericMachineManager;
import com.doubao.statemachine.extend.manager.DefaultStateMachineManagerFactory;
import com.doubao.statemachine.extend.manager.GenericMachineManager;
import com.doubao.statemachine.extend.manager.StateMachineManager;
import com.doubao.statemachine.extend.manager.StateMachineManagerFactory;
import com.doubao.statemachine.extend.support.translator.*;
import com.doubao.statemachine.web.controller.StateMachineController;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * auto config stateMachine
 *
 * @author doubao
 * @date 2021/05/04 18:38
 */
@Configuration
@EnableConfigurationProperties(StateMachineProperties.class)
@SuppressWarnings({"rawtypes"})
public class StateMachineAutoConfig {
    @Bean
    @ConditionalOnMissingBean
    public StateMachineManagerFactory stateMachineFactory(ListableBeanFactory beanFactory) {
        DefaultStateMachineManagerFactory factory = new DefaultStateMachineManagerFactory();
        Map<String, StateMachineConfig> configs = beanFactory.getBeansOfType(StateMachineConfig.class);
        Map<String, StateMachineGlobalConfig> beansOfType = beanFactory.getBeansOfType(StateMachineGlobalConfig.class);
        Map<String, StateMachineGlobalConfig> globalConfigMap = beansOfType.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getValue().getGroup(), Entry::getValue));
        Collection<StateMachineConfig> values = configs.values();
        factory.init(values, globalConfigMap);
        return factory;
    }

    @Bean
    @ConditionalOnMissingBean
    public StateMachineManager stateMachineManager(StateMachineManagerFactory factory) {
        return factory.getStateMachineManager(StateMachineConfig.DEFAULT_GROUP);
    }

    @Bean
    @ConditionalOnBean({StateMachineManager.class})
    @ConditionalOnProperty(name = StateMachineProperties.PREFIX + ".genericManager", havingValue = "true")
    public GenericMachineManager genericStateMachineManager(StateMachineManagerFactory factory,
                                                            StateMachineManager stateMachineManager) {
        return new DefaultGenericMachineManager<>(stateMachineManager);
    }

    @Bean
    @ConditionalOnMissingBean
    public EventTranslatorManager eventTranslatorManager(ListableBeanFactory beanFactory) {
        Map<String, EventTranslator> beansOfType = beanFactory.getBeansOfType(EventTranslator.class);
        return new DefaultEventTranslatorManager(beansOfType.values());
    }

    @Bean
    @ConditionalOnMissingBean
    public StateTranslatorManager stateTranslatorManager(ListableBeanFactory beanFactory) {
        Map<String, StateTranslator> beansOfType = beanFactory.getBeansOfType(StateTranslator.class);
        return new DefaultStateTranslatorManager(beansOfType.values());
    }


    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean({StateTranslator.class, EventTranslator.class, StateMachineManager.class})
    @ConditionalOnWebApplication
    @ConditionalOnProperty(name = StateMachineProperties.PREFIX + ".webserver", havingValue = "true")
    public StateMachineController controller(StateTranslatorManager stateTranslatorManager,
                                             EventTranslatorManager eventTranslatorManager,
                                             StateMachineManager stateMachineManager) {
        return new StateMachineController(stateMachineManager, stateTranslatorManager, eventTranslatorManager);
    }
}
