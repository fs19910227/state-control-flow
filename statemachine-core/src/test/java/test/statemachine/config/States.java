package test.statemachine.config;

import lombok.Getter;

public enum States {
	CANCELED("已取消"),
	START("start"),
	UN_PAID("未支付"),
	PAID("已支付"),
	OPENED("已开通"),
	REFUND_AUDITING("退款审核中"),
	REFUND_SUCCEED("退款成功");

	@Getter
	private final String desc;

	States(String desc) {
		this.desc = desc;
	}

}