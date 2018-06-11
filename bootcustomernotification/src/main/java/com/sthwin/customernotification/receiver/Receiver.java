package com.sthwin.customernotification.receiver;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.sthwin.customernotification.mail.Mailer;

@Component
public class Receiver {
	@Autowired
	Mailer mailer;

	@Bean
	Queue queue() {
		return new Queue("CustomerQ", false);
	}

	@RabbitListener(queues = "CustomerQ")
	public void processMessage(String email) {
		System.out.println(email);
		mailer.sendMail(email);
	}
}
