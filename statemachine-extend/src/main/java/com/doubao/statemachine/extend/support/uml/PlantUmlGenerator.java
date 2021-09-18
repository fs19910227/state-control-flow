package com.doubao.statemachine.extend.support.uml;

import com.doubao.statemachine.extend.support.*;
import com.doubao.statemachine.extend.support.translator.EventTranslator;
import com.doubao.statemachine.extend.util.MachineModelUtil;
import com.doubao.statemachine.action.Action;
import com.doubao.statemachine.config.model.StateMachineModel;
import com.doubao.statemachine.extend.support.translator.StateTranslator;
import com.doubao.statemachine.guard.Guard;
import com.doubao.statemachine.state.StateType;
import com.doubao.statemachine.transition.TransitionKind;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * plant uml data generator
 * generate uml info from stateMachineModel
 *
 * @author doubao
 * @date 2021/4/29
 */
public class PlantUmlGenerator<S, E, C> implements UmlGenerator<S, E, C> {
    private String machineId;
    private List<StateDesc<S>> stateDescList;
    private List<TransitionDesc<S, E, C>> transitionDescList;
    private AtomicInteger internalCounter = new AtomicInteger(0);
    private EventViewMode eventViewMode;
    private boolean showInternal;
    private boolean showAction;

    @Override
    public UmlGenerator<S, E, C> init(UmlGeneratorConfig<S, E, C> generatorConfig) {
        EventTranslator<E> eventTranslator = generatorConfig.getEventTranslator();
        StateMachineModel<S, E, C> stateMachineModel = generatorConfig.getMachineModel();
        StateTranslator<S> stateTranslator = generatorConfig.getStateTranslator();
        UmlGeneratorConfigProperties properties = generatorConfig.getProperties();
        //fill base properties
        this.eventViewMode = properties.getEventViewMode();
        this.showInternal = properties.isShowInternalTransition();
        this.showAction = properties.isShowAction();
        this.machineId = stateMachineModel.getConfigurationData().getMachineId();

        //build states and transitions
        Map<S, StateDesc<S>> stateDescMap = MachineModelUtil.mergeStateData(stateMachineModel, stateTranslator);
        Map<E, EventDesc<E>> eEventDescMap = MachineModelUtil.mergeEventData(stateMachineModel, eventTranslator);
        Map<Class<? extends Guard>, GuardDesc> guardDescMap = MachineModelUtil.mergeGuardData(stateMachineModel);
        Map<Class<? extends Action>, ActionDesc> actionDescMap = MachineModelUtil.mergeActionData(stateMachineModel);
        this.stateDescList = new ArrayList<>(stateDescMap.values());
        this.transitionDescList = MachineModelUtil.mergeTransitionData(stateMachineModel,
                stateDescMap,
                eEventDescMap,
                guardDescMap,
                actionDescMap);
        //refresh state map
        //fill internal target to real state
        for (TransitionDesc<S, E, C> secTransitionDesc : transitionDescList) {
            TransitionKind kind = secTransitionDesc.getKind();
            if (kind == TransitionKind.INTERNAL && showInternal) {
                StateDesc<S> source = secTransitionDesc.getSource();
                StateDesc<S> fakeInternalTarget = fakeState(source, "None_" + internalCounter.getAndIncrement());
                secTransitionDesc.setTarget(fakeInternalTarget);
                stateDescList.add(fakeInternalTarget);
            }
        }
        //insert start and end transitions
        for (StateDesc<S> state : stateDescList) {
            if (state.isStart()) {
                StateDesc<S> fake = fakeState(state, "[*]");
                TransitionDesc<S, E, C> e = new TransitionDesc<>(fake, state, null, null, null, TransitionKind.EXTERNAL);
                transitionDescList.add(e);
            }
            if (state.isFinal()) {
                StateDesc<S> fake = fakeState(state, "[*]");
                TransitionDesc<S, E, C> e = new TransitionDesc<>(state, fake, null, null, null, TransitionKind.EXTERNAL);
                transitionDescList.add(e);
            }
        }
        return this;
    }

    private static <S> StateDesc<S> fakeState(StateDesc<S> source, String name) {
        StateDesc<S> fakeState = new StateDesc<S>()
                .setGroup(source.getGroup())
                .setName(name)
                .setLabel("fake")
                .setType(StateType.NORMAL)
                .setComments(Collections.emptyList())
                .setProperties(Collections.emptyMap())
                .setStart(false)
                .setFinal(false);
        return fakeState;
    }

    private static String start() {
        return "@startuml\n";
    }

    private static String end() {
        return "@enduml\n";
    }

    private static final String LINE = "-->";
    private static final String DASHED_LINE = "-[dashed]->";
    private static final String DOTTED_LINE = "-[dotted]->";

    private static <S> String status(StateDesc<S> status) {
        StringBuilder builder = new StringBuilder();
        switch (status.getType()) {
            case CHOICE:
                builder.append(String.format("state %s <<choice>>", status.getName())).append("\n");
                break;
            case ENTRANCE:
                builder.append(String.format("state %s <<entryPoint>>", status.getName())).append("\n");
                break;
            case EXIT:
                builder.append(String.format("state %s <<exitPoint>>", status.getName())).append("\n");
                break;
            default:
                //add label
                builder.append(status.getName());
                builder.append(" : ");
                builder.append("label:").append(status.getLabel()).append("\n");
                //add properties
                if (!status.getProperties().isEmpty()) {
                    status.getProperties().forEach((k, v) -> {
                        builder.append(status.getName());
                        builder.append(" : ");
                        builder.append(k).append(":").append(v).append("\n");
                    });
                }
                //add comment
                if (!status.getComments().isEmpty()) {
                    for (String comment : status.getComments()) {
                        builder.append(status.getName());
                        builder.append(" : ");
                        builder.append(comment).append("\n");
                    }
                }
        }
        return builder.toString();

    }

    private List<String> action(List<ActionDesc> actions) {
        if (!showAction) {
            return Collections.emptyList();
        }
        if (actions == null) {
            return Collections.emptyList();
        }
        return actions.stream()
                .filter(Objects::nonNull)
                .map(ac -> "action:" + ac.getName())
                .collect(Collectors.toList());
    }


    private String transition(TransitionDesc<S, E, C> transition) {
        StateDesc<S> from = transition.getSource();
        StateDesc<S> to = transition.getTarget();
        GuardDesc guard = transition.getGuard();
        String fromName = from.getName();
        String toName = to.getName();
        if (to.getType() == StateType.HISTORY) {
            toName = toName + "[H]";
        }
        String line = guard == null ? LINE : DOTTED_LINE;
        String guardString = guard == null ? null : "match:" + guard.getName();
        List<String> actions = action(transition.getActions());
        String eventName = null;
        List<String> eventComments = null;
        EventDesc<E> event = transition.getEvent();
        if (event != null) {
            eventComments = event.getComments();
            eventName = event(event);
        }
        return transition(fromName, toName, line, eventName, eventComments, guardString, actions);
    }

    private static String transition(String fromName, String toName, String line, String event, List<String> comments,
                                     String guard,
                                     List<String> actions) {
        StringBuilder builder = new StringBuilder();
        builder.append(fromName)
                .append(" ").append(line).append(" ")
                .append(toName);
        if (event != null || guard != null || (actions != null && !actions.isEmpty())) {
            builder.append(" : ");
            if (event != null) {
                builder.append(event);
                if (comments != null) {
                    for (String comment : comments) {
                        builder.append("\\n").append(comment);
                    }
                }
            }
            if (guard != null) {
                builder.append("\\n").append(guard);
            }
            if (actions != null) {
                for (String action : actions) {
                    builder.append("\\n").append(action);
                }
            }
        }
        builder.append("\n");
        return builder.toString();
    }

    private String event(EventDesc<E> event) {
        String label = event.getLabel();
        String name = event.getName();
        switch (eventViewMode) {
            case NAME_ONLY:
                return name;
            case LABEL_ONLY:
                return label;
            default:
                return name + "(" + label + ")";
        }
    }


    private void groupStatus(String group, List<StateDesc<S>> states, StringBuilder builder) {
        if (!group.equals(StateDesc.DEFAULT_GROUP)) {
            builder.append("state ").append(group).append(" {").append("\n");
        }
        states.stream()
                .sorted(Comparator.comparing(StateDesc::getSort))
                .forEach(state -> builder.append(status(state)));
        if (!group.equals(StateDesc.DEFAULT_GROUP)) {
            builder.append("}\n");
        }
    }

    private void buildContent(StringBuilder builder) {
        //output state
        Map<String, List<StateDesc<S>>> stateMapByGroup = stateDescList.stream()
                .collect(groupingBy(StateDesc::getGroup));
        stateMapByGroup.forEach((group, states) -> {
            groupStatus(group, states, builder);
        });
        //out put transitions
        for (TransitionDesc<S, E, C> secTransitionData : transitionDescList) {
            TransitionKind kind = secTransitionData.getKind();
            if (kind == TransitionKind.EXTERNAL || showInternal) {
                builder.append(transition(secTransitionData));
            }
        }
    }

    @Override
    public String generateUmlString() {
        StringBuilder builder = new StringBuilder();
        builder.append(start());
        builder.append("title ").append(machineId).append("\n");
        buildContent(builder);
        builder.append(end());
        return builder.toString();
    }

}
