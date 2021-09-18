package test.statemachine.cases;

import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import com.doubao.statemachine.Result;
import com.doubao.statemachine.core.StateMachine;
import com.doubao.statemachine.message.Message;
import com.doubao.statemachine.state.State;
import test.statemachine.builder.MachineBuilder;
import test.statemachine.config.Events;
import test.statemachine.config.States;
import test.statemachine.model.TestOrderInfo;

/**
 * @author doubao
 * @date 2021/04/27 22:42
 */
public class StateMachineTest {
	/**
	 * 集成测试，所有特性
	 *
	 * @param args args
	 */
	public static void main(String[] args) {
		StateMachine<States, Events, TestOrderInfo> stateMachine = MachineBuilder.buildMachine();
		States init = States.UN_PAID;
		TestOrderInfo testOrderInfo = new TestOrderInfo();
		testOrderInfo.setOrderNo("test123");
		testOrderInfo.setId(123L);
		testOrderInfo.setStates(init);
		processStateMachine(stateMachine, init, Events::valueOf, () -> testOrderInfo);
	}



	static <E, S, C> void processStateMachine(StateMachine<S, E, C> stateMachine, S init,
		Function<String, E> mapping, Supplier<C> contentSupplier) {
		Scanner scanner = new Scanner(System.in);
		Message<S, E, C> message = Message.of(init, null, contentSupplier.get());
		while (true) {
			Set<E> es = stateMachine.inferNextPossibleEvents(message.getSource());
			System.out.println("==========================================");
			System.out.println("current sate:" + message.getSource());
			System.out.println("possible next event:" + es);
			String cmd = scanner.nextLine();
			String s = cmd.toUpperCase();
			E event;
			try {
				event = mapping.apply(s);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			message.setEvent(event);
			Result<S, E, C> fire = stateMachine.fire(message);
			if (fire.getTargetState() != null) {
				State<S, E, C> secState = fire.getTargetState();
				message.setSource(secState.getId());
			}

		}
	}
}
