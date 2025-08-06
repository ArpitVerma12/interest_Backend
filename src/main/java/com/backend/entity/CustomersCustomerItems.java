//package com.backend.entity;
//
//import java.util.List;
//
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.OneToMany;
//import lombok.Data;
//@Data
//@Entity
//public class CustomersCustomerItems {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
//	private String item_name;
//	@Column(name = "interest")
//	private Double interest;
//	
//	@ManyToOne
//	@JoinColumn(name="customersCustomer_id", referencedColumnName = "customersCust_id")
//	private Mapping mapping;
//	
//
//    @OneToMany(mappedBy = "customersCustomerItems", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<CustomersCustomerWeight> customersCustomerWeight;
//}
