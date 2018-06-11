package com.sthwin.chapter2;

import java.util.Base64;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.test.context.junit4.SpringRunner;

import com.sthwin.chapter2.GreetingController.Greet;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	@Test
	public void testSecureService() {
		String plainCred = "guest:guest123";
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + new String(Base64.getEncoder().encode(plainCred.getBytes())));
		HttpEntity<String> request = new HttpEntity<String>(headers);

		ResponseEntity<Greet> response = restTemplate.exchange("/", HttpMethod.GET, request, Greet.class);
		Assert.assertEquals("Hello World!", response.getBody().getMessage());
	}

	@Test
	public void testVanillaService() {
		Greet greet = restTemplate.getForObject("http://localhost:" + port, Greet.class);
		// Greet greet = restTemplate.getForObject("/", Greet.class); 상태 경로로 사용할 수 있
		Assert.assertEquals("Hello World!", greet.getMessage());
	}

	@Test
	public void testOAuthService() {
		ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
		resource.setUsername("guest");
		resource.setPassword("guest123");
		resource.setAccessTokenUri("http://localhost:" + port + "/oauth/token");
		resource.setClientId("trustedclient");
		resource.setClientSecret("trustedclient123");
		resource.setGrantType("password");

		DefaultOAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();

		OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resource, clientContext);

		Greet greet = restTemplate.getForObject("http://localhost:" + port, Greet.class);

		Assert.assertEquals("Hello World!", greet.getMessage());

	}
}
