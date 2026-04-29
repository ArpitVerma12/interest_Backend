package com.backend.RepositoryHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.backend.Repository.DepositeRepository;
import com.backend.Repository.ItemsRepository;
import com.backend.Repository.NewCustomerRepository;
import com.backend.Repository.SignupLoginRepository;
import com.backend.security.JwtUtils;
import com.backend.services.CustomerItemsWeightData;
import com.backend.services.DepositeMoneyData;
import com.backend.services.ExcelCustomersService;
import com.backend.services.ItemCalculationService;
import com.backend.services.customerItems;


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

	@Autowired
    public ExcelCustomersService excelService;

	@Autowired
	public customerItems custItem;

	@Autowired
	public DepositeMoneyData DepositeData;

	@Autowired
	public CustomerItemsWeightData CustItemWeight;

	@Autowired
	public ItemCalculationService itemCalculation;
}
