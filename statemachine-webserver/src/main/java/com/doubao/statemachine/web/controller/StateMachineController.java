package com.doubao.statemachine.web.controller;

import com.doubao.statemachine.extend.support.uml.*;
import com.doubao.statemachine.config.model.StateMachineModel;
import com.doubao.statemachine.core.StateMachine;
import com.doubao.statemachine.extend.manager.StateMachineManager;
import com.doubao.statemachine.extend.model.MachineMetaInfo;
import com.doubao.statemachine.extend.support.ActionDesc;
import com.doubao.statemachine.extend.support.EventDesc;
import com.doubao.statemachine.extend.support.GuardDesc;
import com.doubao.statemachine.extend.support.StateDesc;
import com.doubao.statemachine.extend.support.translator.EventTranslator;
import com.doubao.statemachine.extend.support.translator.EventTranslatorManager;
import com.doubao.statemachine.extend.support.translator.StateTranslator;
import com.doubao.statemachine.extend.support.translator.StateTranslatorManager;
import com.doubao.statemachine.extend.util.MachineModelUtil;
import com.doubao.statemachine.state.StateType;
import com.doubao.statemachine.web.controller.vo.MachineDetailView;
import com.doubao.statemachine.web.controller.vo.StatesView;
import com.doubao.statemachine.web.controller.vo.UmlView;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;
import net.sourceforge.plantuml.code.Base64Coder;
import net.sourceforge.plantuml.code.TranscoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * StateMachineController
 *
 * @author doubao
 * @date 2021/5/14
 */
@ResponseBody
@RequestMapping("/webjars/statemachine")
@SuppressWarnings({"rawtypes", "unchecked"})
public class StateMachineController {
    private final StateMachineManager stateMachineManager;
    private final StateTranslatorManager stateTranslatorManager;
    private final EventTranslatorManager eventTranslatorManager;
    @Autowired(required = false)
    private UmlGeneratorConfigProperties umlGeneratorConfigProperties;

    public StateMachineController(StateMachineManager stateMachineManager,
                                  StateTranslatorManager stateTranslator,
                                  EventTranslatorManager eventTranslator) {
        this.stateMachineManager = stateMachineManager;
        this.stateTranslatorManager = stateTranslator;
        this.eventTranslatorManager = eventTranslator;
    }

    @GetMapping("/{id}/states")
    public StatesView machineStatesView(@PathVariable("id") String id) {
        MachineMetaInfo machineMetaInfo = stateMachineManager.metaInfoById(id);
        StateMachine byId = stateMachineManager.getById(id);
        return new StatesView(states(machineMetaInfo, byId.getModel()));
    }

    @GetMapping("/{id}/events")
    public List<EventDesc> machineEvents(@PathVariable("id") String id) {
        MachineMetaInfo machineMetaInfo = stateMachineManager.metaInfoById(id);
        StateMachine byId = stateMachineManager.getById(id);
        return events(machineMetaInfo, byId.getModel());
    }

    @GetMapping("/{id}/guards")
    public List<GuardDesc> machineGuards(@PathVariable("id") String id) {
        StateMachine byId = stateMachineManager.getById(id);
        StateMachineModel model = byId.getModel();
        return new ArrayList<>(MachineModelUtil.mergeGuardData(model).values());
    }

    @GetMapping("/{id}/actions")
    public List<ActionDesc> machineActions(@PathVariable("id") String id) {
        StateMachine byId = stateMachineManager.getById(id);
        StateMachineModel model = byId.getModel();
        return new ArrayList<>(MachineModelUtil.mergeActionData(model).values());
    }

    @GetMapping("/metaInfo/")
    public Map<String, List<MachineMetaInfo>> machineMetas() {
        return stateMachineManager.metaInfoList().stream()
                .sorted(Comparator.comparing(MachineMetaInfo::getId))
                .collect(Collectors.groupingBy(MachineMetaInfo::getTag))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (u, v) -> u, LinkedHashMap::new));
    }

    @GetMapping("/metaInfo/{id}")
    public MachineMetaInfo machineMeta(@PathVariable("id") String id) throws IOException {
        return stateMachineManager.metaInfoById(id);
    }

    @PostMapping("/detail/{id}")
    public MachineDetailView machineDetail(@RequestBody UmlView umlConfigView, @PathVariable("id") String id)
            throws IOException {
        MachineMetaInfo machineMetaInfo = stateMachineManager.metaInfoById(id);
        StateMachine byId = stateMachineManager.getById(id);
        StateMachineModel model = byId.getModel();
        UmlView umlView = umlView(machineMetaInfo, model, umlConfigView);
        StatesView statesView = new StatesView(states(machineMetaInfo, model));
        List<EventDesc> events = events(machineMetaInfo, model);
        List<GuardDesc> guardDescList = new ArrayList<>(MachineModelUtil.mergeGuardData(model).values());
        List<ActionDesc> actionDescList = new ArrayList<>(MachineModelUtil.mergeActionData(model).values());

        return new MachineDetailView()
                .setMetaInfo(machineMetaInfo)
                .setUmlView(umlView)
                .setStateView(statesView)
                .setEventDescList(events)
                .setGuardDescList(guardDescList)
                .setActionDescList(actionDescList);
    }

    @PostMapping("/{id}/uml/img")
    public UmlView machineUmlView(@RequestBody UmlView umlConfigView, @PathVariable("id") String id)
            throws IOException {
        StateMachine<Object, Object, Object> byId = stateMachineManager.getById(id);
        MachineMetaInfo machineMetaInfo = stateMachineManager.metaInfoById(id);
        StateMachineModel<Object, Object, Object> model = byId.getModel();
        return umlView(machineMetaInfo, model, umlConfigView);
    }

    private String base64Img(String umlEncode) throws IOException {
        String umlText = TranscoderUtil.getDefaultTranscoder().decode(umlEncode);
        SourceStringReader reader = new SourceStringReader(umlText);
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        reader.outputImage(out, 0, new FileFormatOption(FileFormat.PNG));
        out.close();
        return "data:image/png;base64,"
                + Base64Coder.encodeLines(out.toByteArray()).replaceAll("\\s", "");
    }

    public UmlView umlView(MachineMetaInfo machineMetaInfo, StateMachineModel stateMachineModel, UmlView umlView) throws IOException {
        String umlEncode = umlEncode(machineMetaInfo, stateMachineModel, umlView);
        String base64Img = base64Img(umlEncode);
        umlView.setUmlCode(umlEncode);
        umlView.setUmlImg(base64Img);
        umlView.setEventViewModes(EventViewMode.values());
        return umlView;
    }

    private String umlEncode(MachineMetaInfo machineMetaInfo, StateMachineModel model,
                             UmlView umlConfigView) throws IOException {
        UmlGenerator umlGenerator = UmlGeneratorFactory.getGenerator(
                UmlGeneratorType.PLANT_UML.name());
        UmlGeneratorConfig generatorConfig = new UmlGeneratorConfig<>(
                getStateTranslator(machineMetaInfo),
                getEventTranslator(machineMetaInfo),
                model);
        if (umlConfigView != null) {
            UmlGeneratorConfigProperties umlGeneratorConfigProperties = new UmlGeneratorConfigProperties();
            umlGeneratorConfigProperties.setShowInternalTransition(umlConfigView.isShowInternal())
                    .setShowAction(umlConfigView.isShowAction())
                    .setEventViewMode(umlConfigView.getEventViewMode());
            generatorConfig.setProperties(umlGeneratorConfigProperties);
        } else if (umlGeneratorConfigProperties != null) {
            generatorConfig.setProperties(umlGeneratorConfigProperties);
        }
        umlGenerator.init(generatorConfig);
        return TranscoderUtil.getDefaultTranscoder().encode(umlGenerator.generateUmlString());
    }

    private List<StateDesc<?>> states(MachineMetaInfo machineMetaInfo, StateMachineModel model) {
        Map<Object, StateDesc<Object>> sStateDescMap = MachineModelUtil.mergeStateData(model,
                getStateTranslator(machineMetaInfo));
        Comparator<StateDesc<Object>> comparing = Comparator.comparing(StateDesc::getSort);
        return sStateDescMap.values().stream()
                .filter(s -> s.getType() == StateType.NORMAL)
                .sorted(comparing)
                .collect(Collectors.toList());
    }

    private List<EventDesc> events(MachineMetaInfo machineMetaInfo, StateMachineModel model) {
        Comparator<EventDesc<Object>> comparing = Comparator.comparing(EventDesc::getSort);
        Collection<EventDesc<Object>> values = MachineModelUtil.mergeEventData(model, getEventTranslator(machineMetaInfo))
                .values();
        return values.stream()
                .sorted(comparing)
                .collect(Collectors.toList());
    }

    private StateTranslator getStateTranslator(MachineMetaInfo machineMetaInfo) {
        return stateTranslatorManager.getTranslator(machineMetaInfo.getGroup(), machineMetaInfo.getId());
    }

    private EventTranslator getEventTranslator(MachineMetaInfo machineMetaInfo) {
        return eventTranslatorManager.getTranslator(machineMetaInfo.getGroup(), machineMetaInfo.getId());
    }
}
