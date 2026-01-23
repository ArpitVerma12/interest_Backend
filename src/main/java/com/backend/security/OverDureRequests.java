package com.backend.security;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class OverDureRequests {
	 private Long id;
	 private String itemName;
	 private Double interest;
	 private String giveMoney;
	 private String months;
	 private String RentMoney;
	 private String TotalMoney;
	 private String Name;
	 private String mobileNumber;
	 private String EmailId;
	 private String Address;
	 private String village;
	 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	 private LocalDate date;
	 private String Remark;
	 private BigDecimal remaningAmount;
}
