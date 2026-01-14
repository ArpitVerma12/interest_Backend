package com.backend.DTOs;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class depositeMoney {
private Long id;
private BigDecimal DepositeMoney;
private BigDecimal remainingMoney;
private String remark;
private BigDecimal totalMoney;
}
