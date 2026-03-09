package com.backend.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class DepositeMoney {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private BigDecimal DepositeMoney;
	private LocalDateTime createDate;
	private String Remark;
	@ManyToOne
	@JoinColumn(name = "customer_item_id") 
	private NewCustomerItems newCustomeritems;
}

