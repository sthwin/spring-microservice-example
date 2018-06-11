package com.sthwin.chapter2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class GreetingController {

	private static final Logger logger = LoggerFactory.getLogger(GreetingController.class);

	@Autowired
	Environment env;

	@RequestMapping("/")
	Greet greet() {
		logger.info("bootrest.customproperty " + env.getProperty("bootrest.customproperty"));
		return new Greet("Hello World!");
	}

	static class Greet {
		private String message;

		public Greet() {
		}

		public Greet(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}
}
