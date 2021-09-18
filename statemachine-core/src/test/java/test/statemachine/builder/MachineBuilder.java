package test.statemachine.builder;

import com.doubao.statemachine.StateContext;
import com.doubao.statemachine.config.builders.StateMachineTransitionConfigurer;
import com.doubao.statemachine.core.StateMachine;
import com.doubao.statemachine.core.StateMachineBuilder;
import com.doubao.statemachine.guard.Guard;
import lombok.experimental.UtilityClass;
import test.statemachine.builder.interceptor.TestInterceptor;
import test.statemachine.config.Events;
import test.statemachine.config.States;
import test.statemachine.listenner.TestListener;
import test.statemachine.model.TestOrderInfo;

/**
 * @author doubao
 * @date 2021/4/19
 */
@UtilityClass
public class MachineBuilder {

	public StateMachineBuilder.Builder<States, Events, TestOrderInfo> buildBuilder() {
		StateMachineBuilder.Builder<States, Events, TestOrderInfo> builder = StateMachineBuilder.builder();
		builder.configureConfigurations()
			//支持校验自检
			.withVerifier()
			.enabled(false);
		builder.configureConfigurations()
			.withConfiguration()
			.machineId("id")
			//通过contentStateChanger自动变更TestOrderInfo里的状态，基于拦截器
			.contentStateChanger((s, c) -> {
				c.setStates(s);
			})
			//监听路由事件
			.listener(new TestListener())
			//可以通过interceptor 实现自动保存状态
			.interceptor(new TestInterceptor());

		StateMachineTransitionConfigurer<States, Events, TestOrderInfo> transitionConfigurer
			= builder.configureTransitions();
		transitionConfigurer
			.withExternal()
			.source(States.START).target(States.UN_PAID)
			.event(Events.CREATE);
		transitionConfigurer.withExternal()
			.source(States.UN_PAID).target(States.PAID)
			.event(Events.PAY);
		transitionConfigurer.withExternal()
			.source(States.PAID).target(States.OPENED)
			.event(Events.OPEN);
		//refund
		transitionConfigurer.withExternal()
			.source(States.PAID).target(States.REFUND_AUDITING)
			.event(Events.REFUND_COMMIT);
		transitionConfigurer.withExternal()
			.source(States.OPENED).target(States.REFUND_AUDITING)
			.event(Events.REFUND_COMMIT);
		//通过internal支持响应状态不变的事件
		transitionConfigurer.withInternal()
			.source(States.PAID)
			.event(Events.CHECK)
			.action(context -> {
				System.out.println("internal action");
			});
		transitionConfigurer.withExternal()
			.source(States.REFUND_AUDITING).target(States.REFUND_SUCCEED)
			.event(Events.REFUND_PASS);
		return builder;
	}

	public StateMachine<States, Events, TestOrderInfo> buildMachine() {
		return buildBuilder().build();
	}

	public static class BackToPaid implements Guard<States, Events, TestOrderInfo> {
		@Override
		public boolean evaluate(StateContext<States, Events, TestOrderInfo> context) {
			return true;
		}
	}

}
