package com.backend.entity;

//import java.time.LocalDateTime;
//import java.util.UUID;
//import jakarta.persistence.*;
//import lombok.Data;
//
//@Data
//@Entity
//@Table(name="items")
//public class Items {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
//	private String user_id;
//	private String item_name;
//	private Double weight;
//	private String unit;
//	private Double interest;
//	private LocalDateTime create_at;
//	
//	@ManyToOne
//	@JoinColumn(name="customer_id", referencedColumnName = "user_id")
//	private NewCustomer newCustomer;
//	
//	@ManyToOne
//	@JoinColumn(name="customersCust_id", referencedColumnName = "user_id")
//	private CustomersCustomer customersCustomer;
//	
//	@PrePersist
//	protected void createDate() {
//	    this.create_at = LocalDateTime.now();  
//	}
//	
//	public String generateTemplateIdWithUUID() {
//	    String prefix = "I";
//	    String uuid = UUID.randomUUID().toString().replace("-", ""); // Remove dashes for a cleaner output
//	    user_id = prefix + "-" + uuid.substring(0, 8);  // Limiting UUID to 16 characters
//	    return user_id;
//	}
//}


import java.time.LocalDateTime;
import java.util.*;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Mapping {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String user_id;
	private LocalDateTime create_at;
	
	@OneToOne
	@JoinColumn(name="customer_id", referencedColumnName = "user_id", unique=true)
	private NewCustomer newCustomer;
	
	@OneToOne
	@JoinColumn(name="customersCust_id", referencedColumnName = "user_id", unique=true)
	private CustomersCustomer customersCustomer;
	
	@OneToMany(mappedBy = "mapping", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NewCustomerItems> newCustomerItems;

    @OneToMany(mappedBy = "mapping", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CustomersCustomerItems> customersCustomerItems;
	
	@PrePersist
	protected void createDate() {
	    this.create_at = LocalDateTime.now();  
	}
	
	public String generateTemplateIdWithUUID() {
	    String prefix = "I";
	    String uuid = UUID.randomUUID().toString().replace("-", ""); // Remove dashes for a cleaner output
	    user_id = prefix + "-" + uuid.substring(0, 8);  // Limiting UUID to 16 characters
	    return user_id;
	}
}