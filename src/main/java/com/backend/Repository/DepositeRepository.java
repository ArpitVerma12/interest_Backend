package com.backend.Repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.entity.DepositeMoney;

public interface DepositeRepository extends JpaRepository<DepositeMoney, Long>{

	
	   @Query("SELECT COALESCE(SUM(d.DepositeMoney), 0) " +
	           "FROM DepositeMoney d " +
	           "WHERE d.newCustomeritems.id = :customerId")
	    BigDecimal getTotalDepositByCustomerId(@Param("customerId") Long customerId);
}
