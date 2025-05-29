package com.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entity.NewCustomerItems;

public interface NewCustomerItemItemRepository extends JpaRepository<NewCustomerItems, Long>{

}
