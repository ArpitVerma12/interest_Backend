package com.backend.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
@Table
public class NewCustomer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique=true)
	private String user_id;
	private String Name;
	private String mobileNumber;
	private String EmailId;
	private String Address;
	private String Remark;
	private LocalDateTime create_at;
	
	@PrePersist
	protected void createDate() {
	    this.create_at = LocalDateTime.now();  
	}
	
//	@OneToMany(mappedBy = "newCustomer", cascade = CascadeType.ALL)
//	private List<CustomersCustomer> customersCustomer;
	
	public String generateTemplateIdWithUUID() {
	    String prefix = "C";
	    String uuid = UUID.randomUUID().toString().replace("-", ""); // Remove dashes for a cleaner output
	    user_id = prefix + "-" + uuid.substring(0, 8);  // Limiting UUID to 16 characters
	    return user_id;
	}


}
