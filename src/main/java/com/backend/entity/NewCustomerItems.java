package com.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class NewCustomerItems {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String item_name;
	private Double interest;
	
	@ManyToOne
	@JoinColumn(name="new_customer_id", referencedColumnName = "customer_id")
	private Mapping mapping;
}
