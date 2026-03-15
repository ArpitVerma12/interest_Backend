package com.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.backend.RepositoryHolder.RepositoryBundle;
import com.backend.entity.*;


@RestController
public class items_controller {

	@Autowired
	private RepositoryBundle Repo;



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
