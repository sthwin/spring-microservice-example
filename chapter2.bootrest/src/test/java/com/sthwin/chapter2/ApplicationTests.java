package com.sthwin.chapter2;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.sthwin.chapter2.Application.Greet;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort 
	private int port;

	@Test
	public void testVanillaService() {
		Greet greet = restTemplate.getForObject("http://localhost:" + port, Greet.class);
		// Greet greet = restTemplate.getForObject("/", Greet.class); 상태 경로로 사용할 수 있
		Assert.assertEquals("Hello World!", greet.getMessage());
	}

}
