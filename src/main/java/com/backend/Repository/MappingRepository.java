package com.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.entity.*;

public interface MappingRepository extends JpaRepository<Mapping, Long>{

	@Query(value="select * FROM Mapping where user_id=:user_id", nativeQuery=true)
	NewCustomerItems findByUserId(@Param("user_id") String user_id);

}
