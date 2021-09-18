package test.statemachine.web.support.config;

import lombok.Getter;

public enum States {
	CANCELED("已取消"),
	START("start"),
	UN_PAID("未支付"),
	PAID("已支付"),
	OPENING("开通中"),
	OPENED("已开通"),
	REFUNDING("退款中"),
	REFUND_AUDITING("退款审核中"),
	REFUND_SUCCEED("退款成功"),
	//***********************
	//choice
	//***********************
	choice_on_open("choice_open"),
	choice_on_refund_pass("choice_refund_pass"),
	choice_on_refund_deny("choice_refund_deny"),
	//***********************
	//history
	//***********************
	/**
	 * history focus on paid and opened
	 */
	H("history");

	@Getter
	private final String desc;

	States(String desc) {
		this.desc = desc;
	}

}