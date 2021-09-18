package test.statemachine.spring.cases;

import com.doubao.statemachine.core.StateMachine;
import com.doubao.statemachine.core.StateMachineInstance;
import com.doubao.statemachine.extend.manager.StateMachineManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import test.statemachine.spring.cases.BaseTest.Config;
import test.statemachine.spring.support.config.Events;
import test.statemachine.spring.support.config.OrderTypes;
import test.statemachine.spring.support.config.States;
import test.statemachine.spring.support.executor.Executor;
import test.statemachine.spring.support.model.TestOrderInfo;
import test.statemachine.spring.support.model.TestOrderInfo.TestOrderDetail;
import test.statemachine.spring.support.model.TestOrderInfo.TestOrderMain;

/**
 * @author doubao
 * @date 2021/04/27 22:42
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Config.class)
public class InheritTest extends BaseTest {

	private static TestOrderInfo orderInfo() {
		TestOrderMain common = new TestOrderMain()
			.setOrderNo("123456")
			.setType(OrderTypes.COMPOSITE);
		TestOrderDetail detail = new TestOrderDetail()
			.setOrderNo("sub123456");
		return new TestOrderInfo().setTestOrderMain(common).setTestOrderDetail(detail);
	}

	@Test
	public void testSelectById() {
		StateMachine<States, Events, TestOrderInfo> id = super.getById("inherit#simple");
		printUml(id);
	}

	@Test
	public void testSelectByContent() {
		StateMachine<States, Events, TestOrderInfo> id = super.selectByContent(
			orderInfo());
		printUml(id);
	}

	@Test
	public void testUml() {
		StateMachine<States, Events, TestOrderInfo> id = super.getById("inherit#simple");
		outputUml(id, "inherit");
	}



	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Config.class);
		StateMachineManager machineManager = context.getBean(StateMachineManager.class);
		StateMachine<States, Events, TestOrderInfo> id = machineManager.getById("inherit#simple");
		StateMachine<States, Events, TestOrderInfo> child = id.getChild();
		TestOrderInfo contentContext = orderInfo();
		StateMachineInstance<States, Events, TestOrderInfo> instance = id.newInstanceBuilder()
			.initState(States.START)
			.content(contentContext)
			.build();
		StateMachineInstance<States, Events, TestOrderInfo> childInstance = child.newInstanceBuilder()
			.initState(States.START)
			.content(contentContext)
			.build();
		Executor.processStateMachineInstance(instance, childInstance, Events::valueOf);
	}
}
