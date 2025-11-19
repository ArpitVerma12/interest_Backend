package com.backend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
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
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
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
	public ResponseEntity<?> getNewCustomer() {
	    List<NewCustomer> customers = newCustRepo.findAll();
	    List<Map<String, Object>> resultList = new ArrayList<>();

	    for (NewCustomer customer : customers) {
	        Map<String, Object> customerData = new HashMap<>();
	        customerData.put("user_id", customer.getUser_id());
	        if(customer.getRemark()!=null) {
	        customerData.put("Name", (customer.getName() + "(" + customer.getRemark() + ")"));
	        }
	        else {
	        	customerData.put("Name", customer.getName());
	        }
	        customerData.put("MobileNumber", customer.getMobileNumber());
	        customerData.put("EmailId", customer.getEmailId());
	        customerData.put("create_at", customer.getCreate_at());
	        customerData.put("Village", customer.getVillage());
	        // If needed, add more fields here

	        resultList.add(customerData);
	    }

	    Map<String, Object> response = new HashMap<>();
	    response.put("data", resultList);

	    return ResponseEntity.ok(response); // <-- Fixed line
	}
	
    @GetMapping("/getVillages")
    public List<Map<String, Object>> getAllVillages() {
        String sql = "SELECT id, village_name FROM villages ORDER BY village_name ASC;";
        return jdbcTemplate.queryForList(sql);
    }
}
