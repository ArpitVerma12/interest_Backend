package com.backend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Repository.CustomersCustomerRepository;
import com.backend.Repository.ItemsRepository;
import com.backend.Repository.NewCustomerRepository;
import com.backend.entity.*;


@RestController
public class items_controller {
@Autowired
private ItemsRepository itmesRepo;

@Autowired
private CustomersCustomerRepository CustomersCustRepo;

@Autowired
private NewCustomerRepository newCustRepo;

@PostMapping("/addItems")
public ResponseEntity<?> addItems(@RequestBody Mapping item) {
    // Generate and set unique user_id
    String user_id = item.generateTemplateIdWithUUID();
    while (itmesRepo.findByUserId(user_id) != null) {
        user_id = item.generateTemplateIdWithUUID();
    }
    item.setUser_id(user_id);

    // Ensure NewCustomer is already persisted
    String newCustomerId = item.getNewCustomer().getUser_id();
    NewCustomer existingNewCustomer = newCustRepo.findByUserId1(newCustomerId); // custom repo method

    if (existingNewCustomer == null) {
        return ResponseEntity.badRequest().body("Invalid NewCustomer reference");
    }
    item.setNewCustomer(existingNewCustomer);

    // Ensure CustomersCustomer is already persisted (optional if needed)
    if (item.getCustomersCustomer() != null) {
        String customersCustId =  item.getCustomersCustomer().getUser_id();
        CustomersCustomer existingCustomersCust = CustomersCustRepo.findByUserId1(customersCustId); // custom repo method

        if (existingCustomersCust == null) {
            return ResponseEntity.badRequest().body("Invalid CustomersCustomer reference");
        }
        item.setCustomersCustomer(existingCustomersCust);
    }

    // Save
    itmesRepo.save(item);
    return ResponseEntity.ok().body("Item saved successfully");
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
