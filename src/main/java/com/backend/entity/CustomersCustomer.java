package com.backend.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="customer's_Customer")
public class CustomersCustomer {
 
	@Id
	private Long id;
	@Column(unique=true)
	private String user_id;
	private String Address;
	private String Name;
	private String MobileNumber;
	private String EmailId;
	private String Relation;
	private LocalDateTime create_at;
	@ManyToOne
	@JoinColumn(name="customer_id", referencedColumnName = "user_id")
	private NewCustomer newCustomer;
	
	@PrePersist
	protected void createDate() {
	    this.create_at = LocalDateTime.now();  
	}
	
	public String generateTemplateIdWithUUID() {
	    String prefix = "CC";
	    String uuid = UUID.randomUUID().toString().replace("-", ""); // Remove dashes for a cleaner output
	    user_id = prefix + "-" + uuid.substring(0, 8);  // Limiting UUID to 16 characters
	    return user_id;
	}
}
