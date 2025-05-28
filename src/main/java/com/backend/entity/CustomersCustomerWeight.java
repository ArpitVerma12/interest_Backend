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
public class CustomersCustomerWeight {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Double weight;
	private String unit;
	
	@ManyToOne
	@JoinColumn(name="item_id", referencedColumnName = "id")
	private CustomersCustomerItems CustomersCustomerItems;
}
