package test.statemachine.spring.cases;

import com.doubao.statemachine.core.StateMachine;
import com.doubao.statemachine.extend.model.MachineMetaInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import test.statemachine.spring.cases.BaseTest.Config;
import test.statemachine.spring.support.config.Events;
import test.statemachine.spring.support.config.States;
import test.statemachine.spring.support.model.TestOrderInfo;

/**
 * @author doubao
 * @date 2021/05/14 22:23
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Config.class)
public class UmlTest extends BaseTest {
	@Test
	public void outPutAll() {
		for (MachineMetaInfo machineMetaInfo : machineManager.metaInfoList()) {
			String id = machineMetaInfo.getId();
			StateMachine<States, Events, TestOrderInfo> byId = machineManager.getById(id);
			outputUml(byId, id);
		}
	}
}
