package com.sthwin.customer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.sthwin.customer.domain.Customer;
import com.sthwin.customer.repository.CustomerRepository;
import com.sthwin.customer.sender.Sender;

@Service
@Lazy
public class CustomerRegistrar {

	CustomerRepository customerRepository;
	Sender sender;

	@Autowired
	public CustomerRegistrar(CustomerRepository customerRepository, Sender sender) {
		this.customerRepository = customerRepository;
		this.sender = sender;
	}

	public Customer register(Customer customer) {
		Optional<Customer> existingCustomer = customerRepository.findByName(customer.getName());

		if (existingCustomer.isPresent()) {
			throw new RuntimeException("is already exists");
		} else {
			customerRepository.save(customer);
			System.out.println("Customer's Email Address" + customer.getEmail());
			sender.send(customer.getEmail());
		}
		return customer;
	}
}
