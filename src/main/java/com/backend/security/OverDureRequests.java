package com.backend.security;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class OverDureRequests {
	 private Long id;
	 private String itemName;
	 private BigDecimal interest;
	 private BigDecimal giveMoney;
	 private String months;
	 private BigDecimal RentMoney;
	 private BigDecimal TotalMoney;
	 private String Name;
	 private String mobileNumber;
	 private String EmailId;
	 private String Address;
	 private String village;
	 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	 private LocalDate date;
	 private String Remark;
	 private BigDecimal remaningAmount;
	 private BigDecimal totalremaningAmount;
}
