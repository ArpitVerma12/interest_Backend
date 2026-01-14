package com.backend.RepositoryHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.backend.Repository.DepositeRepository;
import com.backend.Repository.ItemsRepository;
import com.backend.Repository.NewCustomerRepository;

@Component
public class RepositoryBundle {

	

//	@Autowired
//	private CustomersCustomerRepository CustomersCustRepo;

	@Autowired
	public NewCustomerRepository newCustRepo;

	@Autowired
	public ItemsRepository itemsRepo;
	
	@Autowired
	public DepositeRepository depositeRepo;
}
