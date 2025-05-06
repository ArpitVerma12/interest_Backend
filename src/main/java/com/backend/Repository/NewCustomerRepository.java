package com.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.entity.NewCustomer;

public interface NewCustomerRepository extends JpaRepository<NewCustomer, Long>{

	@Query(value="select * FROM new_customer where user_id=:user_id", nativeQuery=true)
	String findByUserId(@Param("user_id") String user_id);

	@Query(value="select * FROM new_customer where email_id=:email", nativeQuery=true)
	NewCustomer findByEmailId(@Param("email") String email);

	@Query(value="select * FROM new_customer where user_id=:user_id", nativeQuery=true)
	NewCustomer findByUserId1(@Param("user_id") String user_id);
}
