//package com.backend.controller;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.backend.Repository.CustomersCustomerRepository;
//import com.backend.Repository.NewCustomerRepository;
//import com.backend.entity.CustomersCustomer;
//import com.backend.entity.NewCustomer;
//
//@RestController
//public class customersCustomer_Controller {
//
//	@Autowired
//	private CustomersCustomerRepository CustomersCustRepo;
//	
//	@Autowired
//	private NewCustomerRepository newCustRepo;
////	@PostMapping("/CustomersCustomer")
////	public ResponseEntity<?> postCustomerData(@RequestBody CustomersCustomer customer){
////		String user_id=customer.generateTemplateIdWithUUID();
////		String email=customer.getEmailId();
////		String existId = CustomersCustRepo.findByUserId(user_id);
////		String customer_id=customer.getNewCustomer().getUser_id();
////		
////		if (existId != null) {
////			user_id = customer.generateTemplateIdWithUUID();
////		}
////		NewCustomer existEmail=CustomersCustRepo.findByEmailId(email);
////		if(existEmail!=null) {
////			return ResponseEntity.badRequest().body("email already exist");		
////		}
////		   String newCustomerUserId = customer.getNewCustomer().getUser_id(); // assuming this is passed
////		    String existingCustomer = newCustRepo.findByUserId(newCustomerUserId);
////		    
////		    if (existingCustomer == null) {
////		        return ResponseEntity.badRequest().body("Invalid customer_id (NewCustomer not found)");
////		    }
////		    customer.setNewCustomer(existingCustomer);
////		return ResponseEntity.ok().body(CustomersCustRepo.save(customer));
////	}
//	
//	@PostMapping("/CustomersCustomer")
//	public ResponseEntity<?> postCustomerData(@RequestBody CustomersCustomer customer) {
//	    // Generate and set user_id
//	    String user_id = customer.generateTemplateIdWithUUID();
//
//	    // Ensure unique user_id (loop until a unique one is found)
//	    while (CustomersCustRepo.findByUserId(user_id) != null) {
//	        user_id = customer.generateTemplateIdWithUUID(); // regenerate
//	    }
//	    customer.setUser_id(user_id);
//	    // Check for existing email
//	    if (CustomersCustRepo.findByEmailId(customer.getEmailId()) != null) {
//	        return ResponseEntity.badRequest().body("Email already exists");
//	    }
//	    
//	    // Validate and set NewCustomer relationship
//	    if (customer.getNewCustomer() == null || customer.getNewCustomer().getUser_id() == null) {
//	        return ResponseEntity.badRequest().body("customer_id is required");
//	    }
//
//
//	    // Get NewCustomer by user_id and validate
//	    String newCustomerUserId = customer.getNewCustomer().getUser_id();
//	    NewCustomer existingCustomer = newCustRepo.findByUserId1(newCustomerUserId);
//
//	    if (existingCustomer == null) {
//	        return ResponseEntity.badRequest().body("Invalid customer_id (NewCustomer not found)");
//	    }
//
//	    // Associate NewCustomer with CustomersCustomer
//	    customer.setNewCustomer(existingCustomer);
//
//	    // Save and return	
//	    CustomersCustRepo.save(customer);
//	    return ResponseEntity.ok("customer data store successfully");
//	}
//
////	@GetMapping("/getCustsCustomer")
////	public ResponseEntity<?> getNewCustomer()
////	{
////		return ResponseEntity.ok().body(CustomersCustRepo.findAll());
////	}
//	
//	@GetMapping("/getCust's_Customer")
//	public ResponseEntity<?> getNewCustomer() {
//	    List<CustomersCustomer> customers = CustomersCustRepo.findAll();
//	    List<Map<String, Object>> resultList = new ArrayList<>();
//
//	    for (CustomersCustomer customer : customers) {
//	        Map<String, Object> customerData = new HashMap<>();
//	        customerData.put("user_id", customer.getUser_id());
//	        customerData.put("Name", customer.getName());
//	        customerData.put("MobileNumber", customer.getMobileNumber());
//	        customerData.put("EmailId", customer.getEmailId());
//	        customerData.put("Relation", customer.getRelation());
//	        customerData.put("create_at", customer.getCreate_at());
//
//	        // Optionally, add information from NewCustomer
//	        NewCustomer newCustomer = customer.getNewCustomer();
//	        if (newCustomer != null) {
//	            customerData.put("newCustomer_user_id", newCustomer.getUser_id());
//	            customerData.put("newCustomer_Name", newCustomer.getName());
//	            customerData.put("newCustomer_Emailid", newCustomer.getEmailId());
//	            customerData.put("newCustomer_MobileNumber", newCustomer.getMobileNumber());
//	        }
//
//	        resultList.add(customerData);
//	    }
//
//	    Map<String, Object> response = new HashMap<>();
//	    response.put("message", "Customers fetched successfully");
//	    response.put("data", resultList);
//
//	    return ResponseEntity.ok(response);
//	}
//	
//	
//	
//	
//
//
//}
