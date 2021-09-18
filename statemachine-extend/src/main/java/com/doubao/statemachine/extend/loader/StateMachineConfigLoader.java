package com.doubao.statemachine.extend.loader;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

import com.doubao.statemachine.extend.holder.DefaultStateMachineHolder;
import com.doubao.statemachine.config.builders.StateMachineConfigurationConfigurer;
import com.doubao.statemachine.config.builders.StateMachineStateBuilder;
import com.doubao.statemachine.config.builders.StateMachineTransitionConfigurer;
import com.doubao.statemachine.config.model.ConfigurationData;
import com.doubao.statemachine.config.model.StateMachineModel;
import com.doubao.statemachine.core.StateMachineBuilder;
import com.doubao.statemachine.core.StateMachineBuilder.Builder;
import com.doubao.statemachine.core.StateMachineFactory;
import com.doubao.statemachine.extend.annotation.MachineConfig;
import com.doubao.statemachine.extend.config.StateMachineConfig;
import com.doubao.statemachine.extend.config.StateMachineGlobalConfig;
import com.doubao.statemachine.extend.holder.StateMachineHolder;
import com.doubao.statemachine.extend.model.MachineConfigData;
import com.doubao.statemachine.extend.model.MachineMetaInfo;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import static java.util.stream.Collectors.groupingBy;

/**
 * StateMachineConfigLoader
 *
 * @author doubao
 * @date 2021/4/30
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@UtilityClass
public class StateMachineConfigLoader {
    /**
     * load config,and refresh
     *
     * @param stateMachineConfigs stateMachineConfigs
     * @param globalConfigMap
     * @return stateMachines
     */
    public Map<String, List<StateMachineHolder>> load(Collection<StateMachineConfig> stateMachineConfigs,
                                                      Map<String, StateMachineGlobalConfig> globalConfigMap) {
        Queue<MachineConfigData> configQueue = stateMachineConfigs.stream()
                .map(c -> new MachineConfigData(c, resolveMetaInfo(c)))
                .collect(Collectors.toCollection(LinkedList::new));

        Map<String, DefaultStateMachineHolder> holderMap = new HashMap<>(configQueue.size());
        while (!configQueue.isEmpty()) {
            MachineConfigData configData = configQueue.poll();
            MachineMetaInfo metaInfo = configData.getMetaInfo();
            String group = metaInfo.getGroup();
            String parentId = metaInfo.getParentId();
            if (StringUtils.isNotBlank(parentId)) {
                DefaultStateMachineHolder parent = holderMap.get(group + "@" + parentId);
                if (parent == null) {
                    configQueue.offer(configData);
                } else {
                    DefaultStateMachineHolder machineHolder = buildMachine(configData, parent, globalConfigMap.get(group));
                    holderMap.put(group + "@" + machineHolder.stateMachine().getId(), machineHolder);
                }
            } else {
                DefaultStateMachineHolder machineHolder = buildMachine(configData, null, globalConfigMap.get(group));
                holderMap.put(group + "@" + machineHolder.stateMachine().getId(), machineHolder);
            }
        }
        return holderMap.values().stream()
                .collect(groupingBy(h -> h.metaInfo().getGroup()));
    }

    private DefaultStateMachineHolder buildMachine(MachineConfigData configData, DefaultStateMachineHolder parent,
                                                   StateMachineGlobalConfig globalConfig) {
        StateMachineConfig config = configData.getConfig();
        Builder builder = StateMachineBuilder.builder();
        StateMachineConfigurationConfigurer machineConfigurationConfigurer = builder.configureConfigurations();
        StateMachineTransitionConfigurer transitionConfigurer = builder.configureTransitions();
        StateMachineStateBuilder stateBuilder = builder.configureStates();
        config.configureConfiguration(machineConfigurationConfigurer);
        config.configureTransitions(transitionConfigurer);
        config.configureStates(stateBuilder);

        StateMachineModel stateMachineModel = builder.buildModel();
        //fill model with extra data
        ConfigurationData configurationData = stateMachineModel.getConfigurationData();
        if (parent != null) {
            //set parent
            configurationData
                    .setParent(parent.stateMachine());
        }

        //覆盖id,code config priority>annotation config priority
        if (StringUtils.isNotBlank(configurationData.getMachineId())) {
            configData.getMetaInfo().setId(configurationData.getMachineId());
        } else {
            configurationData.setMachineId(configData.getMetaInfo().getId());
        }
        //fill global config
        if (globalConfig != null) {
            configurationData.getListeners().addAll(globalConfig.getGlobalListeners());
            configurationData.getInterceptors().addAll(globalConfig.getGlobalInterceptors());
        }
        return new DefaultStateMachineHolder(StateMachineFactory.buildStateMachine(stateMachineModel),
                configData.getMetaInfo(),
                config::support, config.priority());
    }

    private static MachineMetaInfo resolveMetaInfo(StateMachineConfig c) {
        MachineConfig annotation = c.getClass().getAnnotation(MachineConfig.class);
        MachineMetaInfo machineMetaInfo = new MachineMetaInfo();
        String id = annotation.id();
        if (StringUtils.isBlank(id)) {
            id = null;
        }
        machineMetaInfo.setId(id);
        machineMetaInfo.setDesc(annotation.desc());
        machineMetaInfo.setTag(annotation.tag());
        machineMetaInfo.setGroup(annotation.group());
        machineMetaInfo.setName(annotation.name());
        machineMetaInfo.setParentId(annotation.parentId());
        return machineMetaInfo;
    }

}
