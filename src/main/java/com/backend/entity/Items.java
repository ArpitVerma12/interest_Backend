package com.backend.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="items")
public class Items {

	@Id
	private Long id;
	private String user_id;
	private String item_name;
	private Double weight;
	private Double interest;
	private LocalDateTime create_at;
	
	@ManyToOne
	@JoinColumn(name="customer_id", referencedColumnName = "user_id")
	private NewCustomer newCustomer;
	
	@ManyToOne
	@JoinColumn(name="customersCust_id", referencedColumnName = "user_id")
	private CustomersCustomer customersCustomer;
	
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
