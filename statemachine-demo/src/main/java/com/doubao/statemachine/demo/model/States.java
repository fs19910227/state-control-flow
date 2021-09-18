package com.doubao.statemachine.demo.model;

import lombok.Getter;

public enum States {
	CANCELED("已取消", 1),
	START("start", 2),
	UN_PAID("未支付", 3),
	PAID("已支付", 4),
	OPENING("开通中", 5),
	OPENED("已开通", 6),
	COMPLETED("已完成", 61),
	REFUNDING("退款中", 7),
	REFUND_AUDITING("退款审核中", 8),
	REFUND_SUCCEED("退款成功", 9),
	//***********************
	//entrance
	//***********************
	in("entrance",-1),
	//***********************
	//exit
	//***********************
	out("exit",-1),
	//***********************
	//choice
	//***********************
	choice_on_open("choice_open", 1),
	choice_on_refund_pass("choice_refund_pass", 1),
	choice_on_refund_deny("choice_refund_deny", 1),
	//***********************
	//history
	//***********************
	/**
	 * history focus on paid and opened
	 */
	HISTORY("history", 1);

	@Getter
	private final String desc;
	@Getter
	private final int code;

	States(String desc, int code) {
		this.desc = desc;
		this.code = code;
	}

}