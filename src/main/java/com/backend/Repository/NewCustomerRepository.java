package com.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.entity.NewCustomer;

@Repository
public interface NewCustomerRepository extends JpaRepository<NewCustomer, Long>{

	@Query(value="select * FROM new_customer where user_id=:user_id", nativeQuery=true)
	String findByUserId(@Param("user_id") String user_id);

	@Query(value="select * FROM new_customer where email_id=:email", nativeQuery=true)
	String findByEmailId(@Param("email") String email);

	@Query(value="select * FROM new_customer where user_id=:user_id", nativeQuery=true)
	NewCustomer findByUserId1(@Param("user_id") String user_id);

	@Query(value="select * from new_customer nc inner join new_customer_items nci on nc.user_id=nci.new_customer_id inner join new_customer_weight ncw on nci.id=ncw.item_id where village=:Village",nativeQuery=true)
	List<NewCustomer> findByVillage(@Param("Village") String Village);

	@Query(value="select user_id from new_customer where user_id=:user_id",nativeQuery =true)
    void deleteByUserId(String user_id);




}
