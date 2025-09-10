package com.backend.Repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.entity.NewCustomerItems;

public interface ItemsRepository extends JpaRepository<NewCustomerItems, Long>{

	@Query(value = "SELECT i.*, n.* FROM new_customer_items i INNER JOIN new_customer n ON  n.user_id=i.new_customer_id WHERE i.new_customer_id = :customerId", nativeQuery = true)
	List<NewCustomerItems> findByCustomer(@Param("customerId") String customerId);


}
