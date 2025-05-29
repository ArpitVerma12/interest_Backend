package com.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Repository.CustomersCustomerRepository;
import com.backend.Repository.NewCustomerRepository;
import com.backend.entity.CustomersCustomer;

@RestController
public class overDueController {

	@Autowired
	private CustomersCustomerRepository CustomersCustRepo;
	
	@Autowired
	private NewCustomerRepository newCustRepo;
	
	
	@GetMapping("/getByCustomerName")
	public ResponseEntity<?> fetchCustmerName(@RequestParam(name = "customer_id") String customer_id)
	{
		List<CustomersCustomer> customers=CustomersCustRepo.findByCustomerId(customer_id);
		
		return ResponseEntity.accepted().body(customers);
	}
	
	
//	@PostMapping
//	public ResponseEntity<?> fetchNewCustomer(@RequestBody String CustomerId)
//	{
//		return ResponseEntity.ok().body(newCustRepo.findBy);
//	}
}
