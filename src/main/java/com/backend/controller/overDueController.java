package com.backend.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.backend.DTOs.depositeMoney;
import com.backend.RepositoryHolder.RepositoryBundle;
import com.backend.entity.DepositeMoney;
import com.backend.entity.NewCustomerItems;
import com.backend.security.OverDureRequests;

@RestController
public class overDueController {

	@Autowired
	private RepositoryBundle Repo;
	

	@GetMapping("/fetchItems")
	public ResponseEntity<?> fetchNewCustomer(@RequestParam("customerId") String customerId)
	{
		List<NewCustomerItems> items=Repo.itemsRepo.findByCustomer(customerId);
		
		 List<OverDureRequests> dtoList = items.stream()
			        .map(item -> {
			            OverDureRequests dto = new OverDureRequests();
			            LocalDateTime createdAtDateTime = item.getCreate_at();
			            String durationStr = "N/A";
			            double monthForRent;
			            LocalDate createdDate = null;
			            LocalDate customDate = null;
			            
			            
			            if (createdAtDateTime != null ) {
			            	createdDate = createdAtDateTime.toLocalDate();
			                LocalDate today = LocalDate.now();
			                customDate = item.getCustomDate();
			                
			            	if(item.getRemainingMoney()!=null) {
			            		DepositeMoney depositeDate=Repo.depositeRepo.findByCreateDate(item.getId());
			            		customDate = depositeDate.getCreateDate().toLocalDate();
			            	}
			            
			                long totalDays;
			                long years;
			                long months;
			                long days;
			                Period period;
			                if(customDate!=null) {
			       
			                 totalDays = ChronoUnit.DAYS.between(customDate , today);
			                 period = Period.between(customDate , today);
			                
		                     
			                }
			                else {
			                	totalDays = ChronoUnit.DAYS.between(createdDate , today);
				                period = Period.between(createdDate , today);
				                
			                     
			                }
			                years = period.getYears();
		                     months = period.getMonths();
		                     days = period.getDays();
		                     
//			                if (totalDays < 30) {
//			                    // Less than 30 days, still count as 1 month
//			                    durationStr = totalDays + " days (charged as 1 month)";
//			                    monthForRent = 1;
//			                } else if(days < 30){
//			                   
//			                    System.out.println("MONTH:"+months);
//			                    durationStr = years + " years, " + months + " months, " + days + " days";
//			                    monthForRent = (years * 12) + months + (days < 30 ? 1 : 0); // add extra month if days ≥ 30
//			               System.out.println("monthForRent:"+monthForRent);
//			                }
		                     BigDecimal rentMoney = null;
		                     durationStr = years + " years, " + months + " months, " + days + " days";
		                     if(item.getGiveMoney()!=null && item.getInterest()!=null) {
		                     if (totalDays < 30) {
//		                    	 rentMoney=(((Double.parseDouble(item.getGiveMoney()) * item.getInterest())/100)/30)*totalDays;
		                    	 rentMoney=(((((item.getGiveMoney()).multiply( item.getInterest())).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)).divide(BigDecimal.valueOf(30), 2, RoundingMode.HALF_UP)).multiply(BigDecimal.valueOf(totalDays)));
		                     }
		                     
		                     else {
			                    
			                    monthForRent = (years * 12) + months + (days/30.0); 
			                    rentMoney=((item.getGiveMoney().multiply(item.getInterest())).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)).multiply(BigDecimal.valueOf(monthForRent));
			            }
			            
			            
		                     NewCustomerItems exists=Repo.itemsRepo.findById(item.getId()).orElse(item);
		                     //NewCustomerItems itemss=new NewCustomerItems();
		                     exists.setTime(durationStr);
		                     BigDecimal totalMoney=null;
		              if(item.getRemainingMoney()==null) {
			            totalMoney = item.getGiveMoney().add(rentMoney);
			            exists.setTotalMoney(totalMoney);
			        	
		               }
		              else {
		            	  totalMoney=item.getRemainingMoney().add(rentMoney);
		            	  exists.setTotalMoney(totalMoney);
		              }
		              
		              	exists.setRentMoney(rentMoney);
			            Repo.itemsRepo.save(exists);
			            
			            dto.setId(item.getId());
			            dto.setItemName(item.getItem_name());
			            dto.setInterest(item.getInterest());
			            dto.setGiveMoney(item.getGiveMoney());
			            dto.setMonths(String.valueOf(durationStr));
			            dto.setRentMoney(rentMoney); 
			            dto.setTotalMoney(totalMoney); 
			            dto.setAddress(item.getNewCustomer().getAddress());
			            dto.setName(item.getNewCustomer().getName());
			            dto.setEmailId(item.getNewCustomer().getEmailId());
			            dto.setDate(customDate!=null? customDate : createdDate);
			            dto.setRemark(item.getRemark());
			            dto.setVillage(item.getNewCustomer().getVillage());
			            dto.setMobileNumber(item.getNewCustomer().getMobileNumber());
			            System.out.println("AAA"+item.getRemainingMoney());
			            if(item.getRemainingMoney()==null) {
			            	System.out.println("NULL");
			            	dto.setRemaningAmount(item.getTotalMoney());
			            }
			            else {
			            	System.out.println("NOT");
			            dto.setRemaningAmount(item.getRemainingMoney());
			            dto.setTotalMoney(item.getRemainingMoney().add(rentMoney));
			            dto.setTotalremaningAmount(item.getRemainingMoney().add(rentMoney));
			            }
			            return dto;
			            
			            }
			            } 
			            return null;
			        })
			        .filter(Objects::nonNull)
			        .toList();
		 if (dtoList.isEmpty()) {
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data not found");
		    }
		return ResponseEntity.ok().body(dtoList);
	}
	
	
	@PostMapping("/depositeMoney")
	public ResponseEntity<?> depositMoney(@RequestBody depositeMoney deposite){
		
		NewCustomerItems item = Repo.itemsRepo.findById(deposite.getId()).orElse(null);
		if(item==null) {
			return ResponseEntity.badRequest().body("not found");
		}
		BigDecimal totalDeposit =Repo.depositeRepo.getTotalDepositByCustomerId(deposite.getId());
		//BigDecimal totalDeposit =Repo.depositeRepo.getTotalDepositByCustomerId(deposite.getId());
		//BigDecimal total=totalDeposit.add(deposite.getDepositeMoney());
		if (deposite.getDepositeMoney().compareTo(item.getTotalMoney()) > 0) {
			return ResponseEntity.badRequest().body("deposit money can't be greater than total money");
		}
		DepositeMoney deposites=new DepositeMoney();
		if (deposite.getDepositeMoney() == null) {
	        return ResponseEntity.badRequest().body("Deposit amount is required");
	    }

	    if (deposite.getTotalMoney() == null) {
	        return ResponseEntity.badRequest().body("Total money is not set");
	    }
	    
	    
	    System.out.println("TOTALD"+totalDeposit);
		BigDecimal remainingMoney=deposite.getTotalMoney().subtract(deposite.getDepositeMoney());
		//BigDecimal totals = (totalDeposit.add(deposite.getDepositeMoney())).setScale(0, RoundingMode.DOWN);
		BigDecimal expected = remainingMoney.setScale(0, RoundingMode.DOWN);
		//System.out.println("TOTAL"+totals);
		System.out.println("EXPECTED"+expected);
		if (expected.intValue()==0) {
		    item.setStatus("completed");
		}

		item.setRemark(deposite.getRemark());
		item.setRemainingMoney(remainingMoney);
		item.setTotalMoney(remainingMoney);
		deposites.setNewCustomeritems(item);
		deposites.setDepositeMoney(deposite.getDepositeMoney());
		deposites.setCreateDate(LocalDateTime.now());
		//deposites.setNewCustomeritems();
		Repo.itemsRepo.save(item);
		Repo.depositeRepo.save(deposites);
		return ResponseEntity.ok().body("successfully");
		
	}
}
