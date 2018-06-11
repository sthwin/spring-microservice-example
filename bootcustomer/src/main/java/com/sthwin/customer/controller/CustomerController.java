package com.sthwin.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sthwin.customer.domain.Customer;
import com.sthwin.customer.service.CustomerRegistrar;

@RestController
public class CustomerController {

	@Autowired
	CustomerRegistrar customerRegistrar;

	@RequestMapping(path = "/register", method = RequestMethod.POST)
	Customer register(@RequestBody Customer customer) {
		return customerRegistrar.register(customer);
	}
}
