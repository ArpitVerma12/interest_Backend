package com.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.entity.Items;

public interface ItemsRepository extends JpaRepository<Items, Long>{

	@Query(value="select * FROM items where user_id=:user_id", nativeQuery=true)
	Items findByUserId(@Param("user_id") String user_id);

}
