package com.backend.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class NewCustomerItems {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String item_name;
	@Column(name = "interest")
	private Double interest;
	
	@ManyToOne
	@JoinColumn(name="new_customer_id", referencedColumnName = "customer_id")
	private Mapping mapping;
	

    @OneToMany(mappedBy = "newCustomerItems", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NewCustomerWeight> newCustomerWeight;
}
