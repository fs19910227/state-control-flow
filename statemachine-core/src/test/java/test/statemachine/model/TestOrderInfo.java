package test.statemachine.model;

import lombok.Data;
import lombok.ToString;
import test.statemachine.config.States;

/**
 * @author doubao
 * @date 2021/4/29
 */
@Data
@ToString
public class TestOrderInfo {
	private Long id;
	private String orderNo;
	private States states;
}
