package com.backend.entity;

import java.time.LocalDateTime;
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
import jakarta.persistence.PrePersist;
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
	private String giveMoney;
	private String takeMoney;
	private String remainingMoney;
	private String status;
	private LocalDateTime create_at;
	
	@PrePersist
	protected void createDate() {
	    this.create_at = LocalDateTime.now();  
	}
	@ManyToOne
	@JoinColumn(name="new_customer_id", referencedColumnName = "user_id")
	private NewCustomer newCustomer;
	

    @OneToMany(mappedBy = "newCustomerItems", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NewCustomerWeight> newCustomerWeight;
}
