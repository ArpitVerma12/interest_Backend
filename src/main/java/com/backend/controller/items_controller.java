package com.backend.controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



import com.backend.RepositoryHolder.RepositoryBundle;
import com.backend.entity.*;

import jakarta.xml.ws.Response;



@RestController
public class items_controller {

	@Autowired
	private RepositoryBundle Repo;




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

//@PostMapping("/addItems")
//public ResponseEntity<?> addItems(@RequestBody Mapping item) {
//    try {
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(item);
//        System.out.println("Received request in saveLinkConsumer method. Request Body: " + json);
//
//        // Validate and set NewCustomer
//        NewCustomer inputNewCustomer = item.getNewCustomer();
//        if (inputNewCustomer != null || inputNewCustomer.getUser_id() != null) {
//            //return ResponseEntity.badRequest().body("NewCustomer is required");
//        	 NewCustomer existingNewCustomer = newCustRepo.findByUserId1(inputNewCustomer.getUser_id());
//             if (existingNewCustomer == null) {
//                 return ResponseEntity.badRequest().body("Invalid NewCustomer reference");
//             }
//        	  if (item.getNewCustomerItems() != null) {
//                  for (NewCustomerItems nci : item.getNewCustomerItems()) {
//                      nci.setMapping(mappingToSave);  // back-reference
//                      if (nci.getNewCustomerWeight() != null) {
//                          for (NewCustomerWeight weight : nci.getNewCustomerWeight()) {
//                              weight.setNewCustomerItems(nci);
//                          }
//                      }
//                  }
//                  mappingToSave.setNewCustomerItems(item.getNewCustomerItems());
//              }
//        }
//
//       
//
//        // Check if a Mapping already exists for this NewCustomer
//       // Mapping existingMapping = itmesRepo.findByNewCustomer(existingNewCustomer);
//        //Mapping mappingToSave = (existingMapping != null) ? existingMapping : item;
//
//        // Set user_id (only if creating new mapping)
//        if (existingMapping == null) {
//            String userId = item.generateTemplateIdWithUUID();
//            while (itmesRepo.findByUserId(userId) != null) {
//                userId = item.generateTemplateIdWithUUID();
//            }
//            mappingToSave.setUser_id(userId);
//        }
//
//        mappingToSave.setNewCustomer(existingNewCustomer);
//
//        // Validate and set CustomersCustomer (if present)
//        if (item.getCustomersCustomer() != null && item.getCustomersCustomer().getUser_id() != null) {
//            CustomersCustomer existingCustomersCustomer =
//                    CustomersCustRepo.findByUserId1(item.getCustomersCustomer().getUser_id());
//
//            if (existingCustomersCustomer == null) {
//                return ResponseEntity.badRequest().body("Invalid CustomersCustomer reference");
//            }
//
//            mappingToSave.setCustomersCustomer(existingCustomersCustomer);
//        }
//
//        // Process NewCustomerItems and their Weights
//      
//
//        // Process CustomersCustomerItems and their Weights
//        if (item.getCustomersCustomerItems() != null) {
//            for (CustomersCustomerItems nci : item.getCustomersCustomerItems()) {
//                nci.setMapping(mappingToSave);
//                if (nci.getCustomersCustomerWeight() != null) {
//                    for (CustomersCustomerWeight weight : nci.getCustomersCustomerWeight()) {
//                        weight.setCustomersCustomerItems(nci);
//                    }
//                }
//            }
//            mappingToSave.setCustomersCustomerItems(item.getCustomersCustomerItems());
//        }
//
//        // Save or update Mapping
//        itmesRepo.save(mappingToSave);
//
//        return ResponseEntity.ok(existingMapping != null
//                ? "Mapping updated successfully"
//                : "Item and related entities saved successfully");
//
//    } catch (Exception e) {
//        e.printStackTrace();
//        return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
//    }
//}

//@PostMapping("/addItems")
//public ResponseEntity<?> addItems(@RequestBody Mapping item) {
//    try {
////        ObjectMapper objectMapper = new ObjectMapper();
////        String json = objectMapper.writeValueAsString(item);
////        System.out.println("Received request in saveLinkConsumer method. Request Body: " + json);
//
//        Mapping mappingToSave;
//        Mapping existingMapping = null;
//
//        NewCustomer existingNewCustomer = null;
////        CustomersCustomer existingCustomersCustomer = null;
//
//        // Fetch NewCustomer
//        if (item.getNewCustomer() != null && item.getNewCustomer().getUser_id() != null) {
//            existingNewCustomer = newCustRepo.findByUserId1(item.getNewCustomer().getUser_id());
//            if (existingNewCustomer == null) {
//                return ResponseEntity.badRequest().body("Invalid NewCustomer reference");
//            }
//        }
//
//        // Fetch CustomersCustomer
////        if (item.getCustomersCustomer() != null && item.getCustomersCustomer().getUser_id() != null) {
////            existingCustomersCustomer = CustomersCustRepo.findByUserId1(item.getCustomersCustomer().getUser_id());
////            if (existingCustomersCustomer == null) {
////                return ResponseEntity.badRequest().body("Invalid CustomersCustomer reference");
////            }
////        }
//System.out.println("h1");
//        // ✅ Try to find mapping with both NewCustomer and CustomersCustomer linked together
//        if (existingNewCustomer != null && existingCustomersCustomer != null) {
//            // Ensure they are logically linked
//            NewCustomer linkedCustomer = existingCustomersCustomer.getNewCustomer();
//            if (linkedCustomer != null && linkedCustomer.getUser_id().equals(existingNewCustomer.getUser_id())) {
//                existingMapping = itmesRepo.findByNewCustomerAndCustomersCustomer(existingNewCustomer, existingCustomersCustomer);
//            }
//        }
//        System.out.println("h2");
//        // ✅ If no mapping found, try with only newCustomer
//        if (existingMapping == null && existingNewCustomer != null) {
//            existingMapping = itmesRepo.findByNewCustomer(existingNewCustomer);
//        }
//        System.out.println("h3");
//        // ✅ If still no mapping, try with only customersCustomer
//        if (existingMapping == null && existingCustomersCustomer != null) {
//            existingMapping = itmesRepo.findByCustomersCustomer(existingCustomersCustomer);
//        }
//        System.out.println("h4");
//        // ✅ If still no mapping, create new
//        if (existingMapping == null) {
//            mappingToSave = new Mapping();
//            String userId = item.generateTemplateIdWithUUID();
//            while (itmesRepo.findByUserId(userId) != null) {
//                userId = item.generateTemplateIdWithUUID();
//            }
//            mappingToSave.setUser_id(userId);
//        } else {
//            mappingToSave = existingMapping;
//        }
//        System.out.println("h5");
//        // Set customers
//        if (existingNewCustomer != null && mappingToSave.getNewCustomer() == null) {
//            mappingToSave.setNewCustomer(existingNewCustomer);
//        }
//        System.out.println("h6");
//        if (existingCustomersCustomer != null && mappingToSave.getCustomersCustomer() == null) {
//            mappingToSave.setCustomersCustomer(existingCustomersCustomer);
//        }
//        System.out.println("h7");
//        // Handle newCustomerItems
//        if (item.getNewCustomerItems() != null && !item.getNewCustomerItems().isEmpty()) {
//        	System.out.println("h8");
//            for (NewCustomerItems nci : item.getNewCustomerItems()) {
//            	System.out.println("h9");
//                nci.setMapping(mappingToSave);
//                if (nci.getNewCustomerWeight() != null) {
//                    for (NewCustomerWeight weight : nci.getNewCustomerWeight()) {
//                        weight.setNewCustomerItems(nci);
//                        System.out.println("h10");
//                    }
//                }
//            }
//            System.out.println("h11");
//            if (mappingToSave.getNewCustomerItems() == null) {
//                mappingToSave.setNewCustomerItems(new ArrayList<>());
//            }
//            System.out.println("h12");
//            mappingToSave.getNewCustomerItems().addAll(item.getNewCustomerItems());
//        }
//        System.out.println("h13");
//        // Handle customersCustomerItems
//        if (item.getCustomersCustomerItems() != null && !item.getCustomersCustomerItems().isEmpty()) {
//        	System.out.println("h14");
//            for (CustomersCustomerItems cci : item.getCustomersCustomerItems()) {
//            	System.out.println("h15");
//                cci.setMapping(mappingToSave);
//                if (cci.getCustomersCustomerWeight() != null) {
//                	System.out.println("h15");
//                    for (CustomersCustomerWeight weight : cci.getCustomersCustomerWeight()) {
//                        weight.setCustomersCustomerItems(cci);
//                        System.out.println("h16");
//                    }
//                }
//            }
//            System.out.println("h17");
//            if (mappingToSave.getCustomersCustomerItems() == null) {
//            	System.out.println("h18");
//                mappingToSave.setCustomersCustomerItems(new ArrayList<>());
//            }
//            System.out.println("h19");
//            mappingToSave.getCustomersCustomerItems().addAll(item.getCustomersCustomerItems());
//        }
//        System.out.println("h20");
//        
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.registerModule(new JavaTimeModule()); // ✅ support for LocalDateTime
//            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // ISO date format
//            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // optional
//            objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // optional
//
//            String mappingJson = objectMapper.writeValueAsString(mappingToSave);
//            System.out.println("MappingToSave Object:\n" + mappingJson);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        itmesRepo.save(mappingToSave);
//        System.out.println("h21");
//        return ResponseEntity.ok(existingMapping != null
//                ? "Mapping updated successfully"
//                : "New mapping created and data saved");
//
//    } catch (Exception e) {
//        e.printStackTrace();
//        return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
//    }
//}

//@PostMapping("/addItems")
//public ResponseEntity<?> addItems(@RequestBody Mapping item) {
//    try {
////        ObjectMapper objectMapper = new ObjectMapper();
////        String json = objectMapper.writeValueAsString(item);
////        System.out.println("Received request in saveLinkConsumer method. Request Body: " + json);
//
//        Mapping mappingToSave;
//        Mapping existingMapping = null;
//
//        NewCustomer existingNewCustomer = null;
////        CustomersCustomer existingCustomersCustomer = null;
//
//        // Fetch NewCustomer
//        if (item.getNewCustomer() != null && item.getNewCustomer().getUser_id() != null) {
//            existingNewCustomer = newCustRepo.findByUserId1(item.getNewCustomer().getUser_id());
//            if (existingNewCustomer == null) {
//                return ResponseEntity.badRequest().body("Invalid NewCustomer reference");
//            }
//        }
//
//       
//System.out.println("h1");
//        // ✅ Try to find mapping with both NewCustomer and CustomersCustomer linked together
//    
//        System.out.println("h2");
//        // ✅ If no mapping found, try with only newCustomer
//        if (existingMapping == null && existingNewCustomer != null) {
//            existingMapping = itmesRepo.findByNewCustomer(existingNewCustomer);
//        }
//        System.out.println("h3");
//    
//        System.out.println("h4");
//        // ✅ If still no mapping, create new
//        if (existingMapping == null) {
//            mappingToSave = new Mapping();
//            String userId = item.generateTemplateIdWithUUID();
//            while (itmesRepo.findByUserId(userId) != null) {
//                userId = item.generateTemplateIdWithUUID();
//            }
//            mappingToSave.setUser_id(userId);
//        } else {
//            mappingToSave = existingMapping;
//        }
//        System.out.println("h5");
//        // Set customers
//        if (existingNewCustomer != null && mappingToSave.getNewCustomer() == null) {
//            mappingToSave.setNewCustomer(existingNewCustomer);
//        }
//        System.out.println("h6");
//        if (existingCustomersCustomer != null && mappingToSave.getCustomersCustomer() == null) {
//            mappingToSave.setCustomersCustomer(existingCustomersCustomer);
//        }
//        System.out.println("h7");
//        // Handle newCustomerItems
//        if (item.getNewCustomerItems() != null && !item.getNewCustomerItems().isEmpty()) {
//        	System.out.println("h8");
//            for (NewCustomerItems nci : item.getNewCustomerItems()) {
//            	System.out.println("h9");
//                nci.setMapping(mappingToSave);
//                if (nci.getNewCustomerWeight() != null) {
//                    for (NewCustomerWeight weight : nci.getNewCustomerWeight()) {
//                        weight.setNewCustomerItems(nci);
//                        System.out.println("h10");
//                    }
//                }
//            }
//            System.out.println("h11");
//            if (mappingToSave.getNewCustomerItems() == null) {
//                mappingToSave.setNewCustomerItems(new ArrayList<>());
//            }
//            System.out.println("h12");
//            mappingToSave.getNewCustomerItems().addAll(item.getNewCustomerItems());
//        }
//        System.out.println("h13");
//        // Handle customersCustomerItems
//        if (item.getCustomersCustomerItems() != null && !item.getCustomersCustomerItems().isEmpty()) {
//        	System.out.println("h14");
//            for (CustomersCustomerItems cci : item.getCustomersCustomerItems()) {
//            	System.out.println("h15");
//                cci.setMapping(mappingToSave);
//                if (cci.getCustomersCustomerWeight() != null) {
//                	System.out.println("h15");
//                    for (CustomersCustomerWeight weight : cci.getCustomersCustomerWeight()) {
//                        weight.setCustomersCustomerItems(cci);
//                        System.out.println("h16");
//                    }
//                }
//            }
//            System.out.println("h17");
//            if (mappingToSave.getCustomersCustomerItems() == null) {
//            	System.out.println("h18");
//                mappingToSave.setCustomersCustomerItems(new ArrayList<>());
//            }
//            System.out.println("h19");
//            mappingToSave.getCustomersCustomerItems().addAll(item.getCustomersCustomerItems());
//        }
//        System.out.println("h20");
//        
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.registerModule(new JavaTimeModule()); // ✅ support for LocalDateTime
//            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // ISO date format
//            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // optional
//            objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // optional
//
//            String mappingJson = objectMapper.writeValueAsString(mappingToSave);
//            System.out.println("MappingToSave Object:\n" + mappingJson);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        itmesRepo.save(mappingToSave);
//        System.out.println("h21");
//        return ResponseEntity.ok(existingMapping != null
//                ? "Mapping updated successfully"
//                : "New mapping created and data saved");
//
//    } catch (Exception e) {
//        e.printStackTrace();
//        return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
//    }
//}


//@GetMapping("/getItems")
//public ResponseEntity<?> getItems() {
//    List<Mapping> customers = itmesRepo.findAll();
//    List<Map<String, Object>> resultList = new ArrayList<>();
//
//    for (Mapping customer : customers) {
//        Map<String, Object> customerData = new HashMap<>();
//        customerData.put("user_id", customer.getUser_id());
////        customerData.put("Item_Name", customer.getItem_name());
////        customerData.put("Weight", customer.getWeight());
////        customerData.put("Unit", customer.getUnit());
////        customerData.put("Interest", customer.getInterest());
//        customerData.put("create_at", customer.getCreate_at());
//
//        // Optionally, add information from NewCustomer
//        NewCustomer newCustomer = customer.getNewCustomer();
//        CustomersCustomer CustomersCustomer = customer.getCustomersCustomer();
//        if (newCustomer != null) {
//            customerData.put("newCustomer_user_id", newCustomer.getUser_id());
//            customerData.put("newCustomer_Name", newCustomer.getName());
//        }
//        if (CustomersCustomer != null) {
//            customerData.put("CustomersCustomer_user_id", CustomersCustomer.getUser_id());
//            customerData.put("CustomersCustomer_Name", CustomersCustomer.getName());
//        }
//
//        resultList.add(customerData);
//    }
//
//    Map<String, Object> response = new HashMap<>();
//    response.put("data", resultList);
//
//    return ResponseEntity.ok(response);
//}

@PostMapping("/addItem")
public ResponseEntity<?> addItem(@RequestBody NewCustomerItems item){
    

	
    if (item.getNewCustomer() != null && item.getNewCustomer().getUser_id() != null) {
        // 🔄 Get the managed (persistent) NewCustomer entity
        NewCustomer existingCustomer = Repo.newCustRepo.findByUserId1(item.getNewCustomer().getUser_id());
        if (existingCustomer == null) {
            return ResponseEntity.badRequest().body("Customer not found");
        }
        // 🔁 Assign the persistent NewCustomer back into the item
        item.setNewCustomer(existingCustomer);
    } else {
        return ResponseEntity.badRequest().body("NewCustomer user_id is required");
    }
    // ✅ Set parent in child
    if (item.getNewCustomerWeight() != null) {
        for (NewCustomerWeight weight : item.getNewCustomerWeight()) {
            weight.setNewCustomerItems(item); // This links the child to parent
        }
    }
    Repo.itemsRepo.save(item);
        return ResponseEntity.ok("items data stored successfully");
    

}

@GetMapping("/getItems")
public ResponseEntity<?> getItems(){
	return ResponseEntity.ok(Repo.itemsRepo.findAll());
}

}
