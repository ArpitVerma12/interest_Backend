package com.backend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Repository.CustomersCustomerRepository;
import com.backend.Repository.MappingRepository;
import com.backend.Repository.NewCustomerRepository;
import com.backend.entity.*;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
public class items_controller {
@Autowired
private MappingRepository itmesRepo;

@Autowired
private CustomersCustomerRepository CustomersCustRepo;

@Autowired
private NewCustomerRepository newCustRepo;




//@PostMapping("/addItems")
//public ResponseEntity<?> addItems(@RequestBody Mapping item) {
//	try {
//	ObjectMapper objectMapper = new ObjectMapper();
//    String json = objectMapper.writeValueAsString(item);
//     System.out.println("Received request in saveLinkConsumer method. Request Body: " + json);
//	System.out.println("hasdffdsasdf");
//	// Generate and set unique user_id
//    String userId = item.generateTemplateIdWithUUID();
//    System.out.println("hasd");
//    while (itmesRepo.findByUserId(userId) != null) {
//        userId = item.generateTemplateIdWithUUID();
//    }
//    System.out.println("h1");
//    item.setUser_id(userId);
//System.out.println("h1");
//    // Validate and set NewCustomer
//    NewCustomer inputNewCustomer = item.getNewCustomer();
//    if (inputNewCustomer == null || inputNewCustomer.getUser_id() == null) {
//        return ResponseEntity.badRequest().body("NewCustomer is required");
//    }
//    System.out.println("h2");
//    NewCustomer existingNewCustomer = newCustRepo.findByUserId1(inputNewCustomer.getUser_id());
//    if (existingNewCustomer == null) {
//        return ResponseEntity.badRequest().body("Invalid NewCustomer reference");
//    }
//    item.setNewCustomer(existingNewCustomer);
//    System.out.println("h3");
//    // Validate and set CustomersCustomer (if present)
//    if (item.getCustomersCustomer() != null && item.getCustomersCustomer().getUser_id() != null) {
//        CustomersCustomer existingCustomersCustomer =
//                CustomersCustRepo.findByUserId1(item.getCustomersCustomer().getUser_id());
//
//        if (existingCustomersCustomer == null) {
//            return ResponseEntity.badRequest().body("Invalid CustomersCustomer reference");
//        }
//
//        item.setCustomersCustomer(existingCustomersCustomer);
//    }
//    System.out.println("h4");
//    // Process NewCustomerItems and their Weights
//    if (item.getNewCustomerItems() != null && item.getNewCustomer().getUser_id() != null) {
//        for (NewCustomerItems nci : item.getNewCustomerItems()) {
//            nci.setMapping(item);  // back-reference to Mapping
//
//            if (nci.getNewCustomerWeight() != null) {
//                for (NewCustomerWeight weight : nci.getNewCustomerWeight()) {
//                    weight.setNewCustomerItems(nci);  // back-reference to NewCustomerItems
//                }
//            }
//        }
//    }
//
//    // Process CustomersCustomerItems and their Weights
//    if (item.getCustomersCustomerItems() != null) {
//        for (CustomersCustomerItems nci : item.getCustomersCustomerItems()) {
//            nci.setMapping(item);  // back-reference to Mapping
//
//            if (nci.getCustomersCustomerWeight() != null) {
//                for (CustomersCustomerWeight weight : nci.getCustomersCustomerWeight()) {
//                    weight.setCustomersCustomerItems(nci);  // back-reference to NewCustomerItems
//                }
//            }
//        }
//    }
//    System.out.println("h5");
//    // Save Mapping and cascade child entities
//    itmesRepo.save(item);
//    return ResponseEntity.ok("Item and related entities saved successfully");
//	}
//	catch (Exception e) {
//        e.printStackTrace(); // For server-side log
//        return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
//    }
//   
//}

@PostMapping("/addItems")
public ResponseEntity<?> addItems(@RequestBody Mapping item) {
    try {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(item);
        System.out.println("Received request in saveLinkConsumer method. Request Body: " + json);

        // Validate and set NewCustomer
        NewCustomer inputNewCustomer = item.getNewCustomer();
        if (inputNewCustomer == null || inputNewCustomer.getUser_id() == null) {
            return ResponseEntity.badRequest().body("NewCustomer is required");
        }

        NewCustomer existingNewCustomer = newCustRepo.findByUserId1(inputNewCustomer.getUser_id());
        if (existingNewCustomer == null) {
            return ResponseEntity.badRequest().body("Invalid NewCustomer reference");
        }

        // Check if a Mapping already exists for this NewCustomer
        Mapping existingMapping = itmesRepo.findByNewCustomer(existingNewCustomer);
        Mapping mappingToSave = (existingMapping != null) ? existingMapping : item;

        // Set user_id (only if creating new mapping)
        if (existingMapping == null) {
            String userId = item.generateTemplateIdWithUUID();
            while (itmesRepo.findByUserId(userId) != null) {
                userId = item.generateTemplateIdWithUUID();
            }
            mappingToSave.setUser_id(userId);
        }

        mappingToSave.setNewCustomer(existingNewCustomer);

        // Validate and set CustomersCustomer (if present)
        if (item.getCustomersCustomer() != null && item.getCustomersCustomer().getUser_id() != null) {
            CustomersCustomer existingCustomersCustomer =
                    CustomersCustRepo.findByUserId1(item.getCustomersCustomer().getUser_id());

            if (existingCustomersCustomer == null) {
                return ResponseEntity.badRequest().body("Invalid CustomersCustomer reference");
            }

            mappingToSave.setCustomersCustomer(existingCustomersCustomer);
        }

        // Process NewCustomerItems and their Weights
        if (item.getNewCustomerItems() != null) {
            for (NewCustomerItems nci : item.getNewCustomerItems()) {
                nci.setMapping(mappingToSave);  // back-reference
                if (nci.getNewCustomerWeight() != null) {
                    for (NewCustomerWeight weight : nci.getNewCustomerWeight()) {
                        weight.setNewCustomerItems(nci);
                    }
                }
            }
            mappingToSave.setNewCustomerItems(item.getNewCustomerItems());
        }

        // Process CustomersCustomerItems and their Weights
        if (item.getCustomersCustomerItems() != null) {
            for (CustomersCustomerItems nci : item.getCustomersCustomerItems()) {
                nci.setMapping(mappingToSave);
                if (nci.getCustomersCustomerWeight() != null) {
                    for (CustomersCustomerWeight weight : nci.getCustomersCustomerWeight()) {
                        weight.setCustomersCustomerItems(nci);
                    }
                }
            }
            mappingToSave.setCustomersCustomerItems(item.getCustomersCustomerItems());
        }

        // Save or update Mapping
        itmesRepo.save(mappingToSave);

        return ResponseEntity.ok(existingMapping != null
                ? "Mapping updated successfully"
                : "Item and related entities saved successfully");

    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
    }
}

@GetMapping("/getItems")
public ResponseEntity<?> getItems() {
    List<Mapping> customers = itmesRepo.findAll();
    List<Map<String, Object>> resultList = new ArrayList<>();

    for (Mapping customer : customers) {
        Map<String, Object> customerData = new HashMap<>();
        customerData.put("user_id", customer.getUser_id());
//        customerData.put("Item_Name", customer.getItem_name());
//        customerData.put("Weight", customer.getWeight());
//        customerData.put("Unit", customer.getUnit());
//        customerData.put("Interest", customer.getInterest());
        customerData.put("create_at", customer.getCreate_at());

        // Optionally, add information from NewCustomer
        NewCustomer newCustomer = customer.getNewCustomer();
        CustomersCustomer CustomersCustomer = customer.getCustomersCustomer();
        if (newCustomer != null) {
            customerData.put("newCustomer_user_id", newCustomer.getUser_id());
            customerData.put("newCustomer_Name", newCustomer.getName());
        }
        if (CustomersCustomer != null) {
            customerData.put("CustomersCustomer_user_id", CustomersCustomer.getUser_id());
            customerData.put("CustomersCustomer_Name", CustomersCustomer.getName());
        }

        resultList.add(customerData);
    }

    Map<String, Object> response = new HashMap<>();
    response.put("data", resultList);

    return ResponseEntity.ok(response);
}


}
