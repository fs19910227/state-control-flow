package com.doubao.statemachine.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * start for demo
 *
 * @a
 */
@SpringBootApplication(scanBasePackages = {"com.doubao.statemachine.demo"})
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
