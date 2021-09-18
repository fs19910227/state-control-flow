package com.doubao.statemachine.config.model;

import java.util.Objects;
import java.util.Set;

import com.doubao.statemachine.state.StateType;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * model contains state config data
 *
 * @author doubao
 * @date 2021/4/28
 */
@Data
@Accessors(chain = true)
public class StateData<S, E, C> {
    private S state;
    private Set<S> refStates;
    private boolean isStart = false;
    private boolean isFinal = false;
    private StateType type;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StateData<?, ?, ?> stateData = (StateData<?, ?, ?>) o;
        return Objects.equals(state, stateData.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state);
    }
}
