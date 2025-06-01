package com.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.entity.*;

public interface MappingRepository extends JpaRepository<Mapping, Long>{

	@Query("select u FROM Mapping u where u.user_id=:user_id")
	Mapping findByUserId(@Param("user_id") String user_id);

	Mapping findByNewCustomer(NewCustomer existingNewCustomer);

}
