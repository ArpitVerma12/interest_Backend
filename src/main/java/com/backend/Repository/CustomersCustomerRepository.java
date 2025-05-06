package com.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.entity.CustomersCustomer;
import com.backend.entity.NewCustomer;

public interface CustomersCustomerRepository extends JpaRepository<CustomersCustomer, Long>{

	@Query(value="select * FROM \"customer's_customer\" where user_id=:user_id", nativeQuery=true)
	String findByUserId(@Param("user_id")  String user_id);
	@Query(value="select * FROM \"customer's_customer\" where email_id=:email", nativeQuery=true)
	NewCustomer findByEmailId(@Param("email") String email);
	
	@Query(value="select * FROM \"customer's_customer\" where user_id=:user_id", nativeQuery=true)
	CustomersCustomer findByUserId1(@Param("user_id") String user_id);

}
