package com.sthwin.chapter2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BootmessagingApplication implements CommandLineRunner {

	@Autowired
	Sender sender;
	
	public static void main(String[] args) {
		SpringApplication.run(BootmessagingApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		sender.send("Hello Messaging..!!!");		
	}
}
