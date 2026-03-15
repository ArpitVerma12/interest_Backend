package com.backend.RepositoryHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.backend.Repository.DepositeRepository;
import com.backend.Repository.ItemsRepository;
import com.backend.Repository.NewCustomerRepository;
import com.backend.Repository.SignupLoginRepository;
import com.backend.security.JwtUtils;

@Component
public class RepositoryBundle {

	


	@Autowired
	public NewCustomerRepository newCustRepo;

	@Autowired
	public ItemsRepository itemsRepo;
	
	@Autowired
	public DepositeRepository depositeRepo;
	
	@Autowired
	public PasswordEncoder passwordEncoder;
	
	@Autowired
	public SignupLoginRepository signupLoginRepo;
	
	@Autowired
	public JwtUtils jwtUtils;
}
