package com.doubao.statemachine.demo;

import com.doubao.statemachine.demo.model.Events;
import com.doubao.statemachine.demo.model.States;
import com.doubao.statemachine.Result;
import com.doubao.statemachine.core.StateMachine;
import com.doubao.statemachine.extend.manager.GenericMachineManager;
import com.doubao.statemachine.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author doubao
 * @date 2021/5/27
 */
@RestController
@RequestMapping("/")
public class TestController {
	@Autowired
	private GenericMachineManager<States, Events, String> machineManager;

	@GetMapping("/hello")
	public String hello() {
		StateMachine<States, Events, String> simple = machineManager.getById("simple");
		Result<States, Events, String> fire = simple.fire(Message.of(States.START, Events.CREATE));
		return fire.getTargetState().getId().name();
	}
}
