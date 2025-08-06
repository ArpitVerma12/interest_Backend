package com.backend.Repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.entity.NewCustomerItems;

public interface ItemsRepository extends JpaRepository<NewCustomerItems, Long>{

	@Query(value = "SELECT * FROM new_customer_items WHERE new_customer_id = :customerId", nativeQuery = true)
	List<NewCustomerItems> findByCustomer(@Param("customerId") String customerId);


}
