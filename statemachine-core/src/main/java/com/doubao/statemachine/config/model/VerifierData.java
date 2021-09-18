package com.doubao.statemachine.config.model;

import java.util.List;

import com.doubao.statemachine.config.model.verifier.StateMachineModelVerifier;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author doubao
 * @date 2021/4/28
 */
@Accessors(chain = true)
@Data
public class VerifierData<S, E, C> {
	private boolean enabled;
	private List<StateMachineModelVerifier<S, E, C>> verifierList;
}
