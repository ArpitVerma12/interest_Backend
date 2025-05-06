package com.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Repository.CustomersCustomerRepository;
import com.backend.Repository.ItemsRepository;
import com.backend.Repository.NewCustomerRepository;
import com.backend.entity.CustomersCustomer;
import com.backend.entity.Items;
import com.backend.entity.NewCustomer;

@RestController
public class items_controller {
@Autowired
private ItemsRepository itmesRepo;

@Autowired
private CustomersCustomerRepository CustomersCustRepo;

@Autowired
private NewCustomerRepository newCustRepo;

@PostMapping("/addItems")
public ResponseEntity<?> addItems(@RequestBody Items item) {
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

}
