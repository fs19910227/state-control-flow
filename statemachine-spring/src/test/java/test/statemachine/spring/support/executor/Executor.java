package test.statemachine.spring.support.executor;

import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import com.doubao.statemachine.Result;
import com.doubao.statemachine.core.StateMachine;
import com.doubao.statemachine.core.StateMachineInstance;
import com.doubao.statemachine.message.Message;
import com.doubao.statemachine.state.State;
import lombok.experimental.UtilityClass;

/**
 * @author zfs
 * @date 2021/05/08 23:02
 */
@UtilityClass
public class Executor {
	public static <E, S, C> void processStateMachineInstance(StateMachineInstance<S, E, C> stateMachine,
		Function<String, E> eventMapping) {
		processStateMachineInstance(stateMachine, null, eventMapping);
	}

	public static <E, S, C> void processStateMachineInstance(StateMachineInstance<S, E, C> stateMachine,
		StateMachineInstance<S, E, C> child,
		Function<String, E> eventMapping) {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			State<S, E, C> secState = stateMachine.currentState();
			Set<E> es = stateMachine.inferNextPossibleEvents();
			System.out.println("==========================================");
			System.out.println("current sate:" + secState.getId());
			if (child != null) {
				System.out.println("current child sate:" + child.currentState().getId());
				es.addAll(child.inferNextPossibleEvents());
			}
			System.out.println("possible next event:" + es);
			String cmd = scanner.nextLine();
			String s = cmd.toUpperCase();
			E event;
			try {
				event = eventMapping.apply(s);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			stateMachine.fire(event);
		}
	}

	public static <E, S, C> void processStateMachine(StateMachine<S, E, C> stateMachine, S init,
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
