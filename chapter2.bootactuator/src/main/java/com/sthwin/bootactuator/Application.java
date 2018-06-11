package com.sthwin.bootactuator;

import java.util.Calendar;
import java.util.concurrent.atomic.LongAdder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}

class TPSCounter {
	LongAdder count;
	int threshold = 2;
	Calendar expiry = null;

	public TPSCounter() {
		this.count = new LongAdder();
		this.expiry = Calendar.getInstance();
		this.expiry.add(Calendar.MINUTE, 1);
	}

	boolean isExpired() {
		return Calendar.getInstance().after(expiry);
	}

	boolean isWeak() {
		return (count.intValue() > threshold);
	}

	void increment() {
		count.increment();
	}
}

@Component
class TPSHealth implements HealthIndicator {

	TPSCounter counter;

	@Override
	public Health health() {
		boolean health = counter.isWeak();
		if (health) {
			return Health.outOfService().withDetail("Too many requests", "OutofService").build();
		}
		return Health.up().build();
	}

	void updateTX() {
		if (counter == null || counter.isExpired()) {
			counter = new TPSCounter();
		}
		counter.increment();
	}
}