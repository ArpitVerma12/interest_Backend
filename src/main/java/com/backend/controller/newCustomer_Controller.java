package com.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Repository.NewCustomerRepository;
import com.backend.entity.*;

@RestController
public class newCustomer_Controller {

	@Autowired
	private NewCustomerRepository newCustRepo;
	
	@PostMapping("/addNewCustomer")
	public ResponseEntity<?> addCustomer(@RequestBody NewCustomer newCust){
		String user_id=newCust.generateTemplateIdWithUUID();
		String email=newCust.getEmailId();
		String existId = newCustRepo.findByUserId(user_id);
		if (existId != null) {
			user_id = newCust.generateTemplateIdWithUUID();
		}
		NewCustomer existEmail=newCustRepo.findByEmailId(email);
		if(existEmail!=null) {
			return ResponseEntity.badRequest().body("email already exist");		
		}
		return ResponseEntity.ok().body(newCustRepo.save(newCust));
	}
	
	@GetMapping("/getNewCustomer")
	public ResponseEntity<?> getNewCustomer()
	{
		return ResponseEntity.ok().body(newCustRepo.findAll());
	}
}
