package com.doubao.statemachine.demo.model;

import java.util.List;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author doubao
 * @date 2021/4/29
 */
@Data
@ToString
@Accessors(chain = true)
public class TestOrderInfo {
	private TestOrderMain testOrderMain;
	private TestOrderDetail testOrderDetail;
	private List<TestOrderDetail> orderDetailList;
	private int refundCompleteTimes = 0;
	private int orderDetailCount = 3;

	@Data
	@ToString
	@Accessors(chain = true)
	public static class TestOrderMain {
		private Long id;
		private String orderNo;
		private States states;
		private OrderTypes type;

	}

	@Data
	@ToString
	@Accessors(chain = true)
	public static class TestOrderDetail {
		private Long id;
		private String mainOrderNo;
		private String orderNo;
		private States states;
	}
}
