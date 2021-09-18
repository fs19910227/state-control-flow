package test.statemachine.spring.support.config;

import lombok.Getter;

/**
 *
 */
public enum Events {
	CANCEL("取消"),
	CREATE("创建"),
	PAY("支付"),
	OPEN("开通"),
	CHECK("自检"),
	REFUND_COMMIT("提交退款"),
	REFUND_DENNY("拒绝退款"),
	REFUND_PASS("同意退款"),
	REFUND_SUCCESS("退款成功");
	@Getter
	private final String desc;

	Events(String desc) {
		this.desc = desc;
	}
}