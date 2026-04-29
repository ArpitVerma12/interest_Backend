package com.backend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Repository.NewCustomerRepository;
import com.backend.RepositoryHolder.RepositoryBundle;
import com.backend.entity.*;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
public class newCustomer_Controller {

	@Autowired
	private NewCustomerRepository newCustRepo;
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	RepositoryBundle Repo;
	
	@PostMapping("/addNewCustomer")
	public ResponseEntity<?> addCustomer(@RequestBody NewCustomer newCust){
		try{
		String user_id=newCust.generateTemplateIdWithUUID();
		String email=newCust.getEmailId();
		String existId = newCustRepo.findByUserId(user_id);
		if (existId != null) {
			user_id = newCust.generateTemplateIdWithUUID();
		}
		if(email!=null && !email.isEmpty()) {
		String existEmail=newCustRepo.findByEmailId(email);
		if(existEmail!=null && !existEmail.isEmpty()) {
			return ResponseEntity.badRequest().body("email already exist");		
		}
		NewCustomer saveData=newCustRepo.save(newCust);
		Repo.excelService.saveCustomerToExcel(saveData);
		}
		return ResponseEntity.ok().body("Add successfully");
	}catch (RuntimeException e) {

        if ("EXCEL_OPEN".equals(e.getMessage())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT) // 🔥 409
                    .body("Please close the Excel file before submitting.");
        }

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Something went wrong");
    }
}
	
	@GetMapping("/getNewCustomer")
	public ResponseEntity<?> getNewCustomer() {
	    List<NewCustomer> customers = newCustRepo.findAll();
	    List<Map<String, Object>> resultList = new ArrayList<>();

	    for (NewCustomer customer : customers) {
	        Map<String, Object> customerData = new HashMap<>();
	        customerData.put("user_id", customer.getUser_id());
	        if(customer.getRemark()!=null && !customer.getRemark().isEmpty()) {
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
	
 @PutMapping("updateCustomer/{user_id}")
 public ResponseEntity<?> updateCustomers(@PathVariable String user_id, @RequestBody NewCustomer cust) {

    NewCustomer existingCustomer = Repo.newCustRepo.findByUserId1(user_id);

    if (existingCustomer == null) {
        return ResponseEntity.notFound().build();
    }

    // Update only fields sent from frontend (non-null fields)

    if (cust.getName() != null) {
        existingCustomer.setName(cust.getName());
    }

    if (cust.getEmailId() != null) {
		System.out.println("here");
        existingCustomer.setEmailId(cust.getEmailId() );
    }

    if (cust.getMobileNumber() != null) {
        existingCustomer.setMobileNumber(cust.getMobileNumber());
    }

    if (cust.getAddress() != null) {
        existingCustomer.setAddress(cust.getAddress());
    }

   if (cust.getRemark() != null) {
        existingCustomer.setRemark(cust.getRemark());
    }
 if (cust.getVillage() != null) {
        existingCustomer.setVillage(cust.getVillage());
    }
    NewCustomer save=Repo.newCustRepo.save(existingCustomer);

    return ResponseEntity.ok(save);
}

// @DeleteMapping("/deleteCustomer/{user_id}")
// public ResponseEntity<?> deleteCustomer(@PathVariable String user_id) {

//     NewCustomer existingCustomer = Repo.newCustRepo.findByUserId1(user_id);

//     if (existingCustomer == null) {
//         return ResponseEntity.status(404).body("Customer not found");
//     }

//     Repo.newCustRepo.delete(existingCustomer);

//     return ResponseEntity.ok("Customer deleted successfully");
// }
    @GetMapping("/getVillages")
    public List<Map<String, Object>> getAllVillages() {
        String sql = "SELECT id, village_name FROM villages ORDER BY village_name ASC;";
        return jdbcTemplate.queryForList(sql);
    }
}
