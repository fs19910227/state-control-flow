package com.doubao.statemachine.extend.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.doubao.statemachine.config.model.StateData;
import com.doubao.statemachine.extend.support.translator.StateTranslator;
import com.doubao.statemachine.state.StateType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * state desc
 *
 * @param <S>
 * @author doubao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class StateDesc<S> {
    public static final String DEFAULT_GROUP = "DEFAULT";
    private int sort = 0;
    private S id;
    private boolean isStart;
    private boolean isFinal;
    private String group = DEFAULT_GROUP;
    private String machineId;
    private StateType type;
    private Set<S> refStates;
    private String name;
    private String label;
    private List<String> comments;
    private Map<String, String> properties;

    public StateDesc<S> appendComment(String comment) {
        if (comments == null) {
            comments = new ArrayList<>();
        }
        comments.add(comment);
        return this;
    }


    public StateDesc(String machineId, StateData<S, ?, ?> stateData, StateTranslator<S> translator) {
        this.machineId = machineId;
        this.id = stateData.getState();
        this.isFinal = stateData.isFinal();
        this.isStart = stateData.isStart();
        this.type = stateData.getType();
        this.refStates = stateData.getRefStates();
        this.name = translator.getName(this.id);
        this.label = translator.getLabel(this);
        this.properties = translator.getProperties(this);
        this.comments = translator.getCommentList(this);
        this.sort = translator.sort(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StateDesc)) {
            return false;
        }
        StateDesc<?> stateDesc = (StateDesc<?>) o;
        return Objects.equals(id, stateDesc.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}