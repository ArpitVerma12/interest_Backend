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

	   @Query(value="SELECT * FROM deposite_money where customer_item_id=:id  ORDER BY create_date DESC LIMIT 1",nativeQuery = true)
	DepositeMoney findByCreateDate(@Param("id") Long id);
}
