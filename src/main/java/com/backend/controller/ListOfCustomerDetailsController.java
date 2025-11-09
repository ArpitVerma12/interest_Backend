package com.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.RepositoryHolder.RepositoryBundle;
import com.backend.entity.NewCustomer;

@RestController
public class ListOfCustomerDetailsController {

	
	@Autowired
	private RepositoryBundle Repo;
	
	
	@GetMapping("/CustomerList")
	public ResponseEntity<?> getList(@RequestParam("Village") String Village){
		List<NewCustomer> fetchDetails=Repo.newCustRepo.findByVillage(Village);
		if(fetchDetails==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("data not found");
		}
		return ResponseEntity.status(HttpStatus.OK).body(fetchDetails);
		
	}
}
