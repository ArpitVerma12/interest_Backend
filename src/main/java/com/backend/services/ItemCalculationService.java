package com.backend.services;


import com.backend.DTOs.OverDureRequests;
import com.backend.Repository.DepositeRepository;
import com.backend.Repository.ItemsRepository;
import com.backend.entity.*;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class ItemCalculationService {

    @Autowired
    private DepositeRepository depositeRepo;

    @Autowired
    private ItemsRepository itemsRepo;

    @Autowired
    private customerItems custItem;

    public OverDureRequests processItem(NewCustomerItems item) {

        OverDureRequests dto = new OverDureRequests();

        // ============================
        // ✅ CASE 1: COMPLETED ITEM
        // ============================
        if ("completed".equalsIgnoreCase(item.getStatus())) {

            dto.setId(item.getId());
            dto.setItemName(item.getItem_name());
            dto.setInterest(item.getInterest());
            dto.setGiveMoney(item.getGiveMoney());
            dto.setMonths(item.getTime());
            dto.setRentMoney(item.getRentMoney());
            dto.setTotalMoney(item.getTotalMoney());
            dto.setStatus(item.getStatus());
            dto.setName(item.getNewCustomer().getName());
            dto.setAddress(item.getNewCustomer().getAddress());
            dto.setMobileNumber(item.getNewCustomer().getMobileNumber());

            dto.setRemaningAmount(item.getTotalMoney());

            dto.setDate(
                item.getCustomDate() != null
                ? item.getCustomDate()
                : item.getCreate_at().toLocalDate()
            );

            return dto; // 🔥 NO CALCULATION
        }

        // ============================
        // ✅ CASE 2: ACTIVE ITEM
        // ============================

        LocalDate createdDate = item.getCreate_at().toLocalDate();
        LocalDate today = LocalDate.now();
        LocalDate customDate = item.getCustomDate();

        if (item.getRemainingMoney() != null) {
            DepositeMoney dep =depositeRepo.findByCreateDate(item.getId());
            if (dep != null && dep.getCreateDate() != null) {
                customDate = dep.getCreateDate().toLocalDate();
            }
        }

        long totalDays;
        Period period;

        if (customDate != null) {
            totalDays = ChronoUnit.DAYS.between(customDate, today);
            period = Period.between(customDate, today);
        } else {
            totalDays = ChronoUnit.DAYS.between(createdDate, today);
            period = Period.between(createdDate, today);
        }

        long years = period.getYears();
        long months = period.getMonths();
        long days = period.getDays();

        String durationStr = years + " years, " + months + " months, " + days + " days";

        BigDecimal rentMoney = BigDecimal.ZERO;

        if (item.getGiveMoney() != null && item.getInterest() != null) {

            if (totalDays < 30) {
                rentMoney = item.getGiveMoney()
                        .multiply(item.getInterest())
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)
                        .divide(BigDecimal.valueOf(30), 2, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(totalDays));
            } else {
                double monthForRent = (years * 12) + months + (days / 30.0);

                rentMoney = item.getGiveMoney()
                        .multiply(item.getInterest())
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(monthForRent));
            }
        }

        // ============================
        // ✅ UPDATE ENTITY
        // ============================
        NewCustomerItems exists = itemsRepo.findById(item.getId()).orElse(item);

        exists.setTime(durationStr);

        BigDecimal totalMoney;

        if (item.getRemainingMoney() == null) {
            totalMoney = item.getGiveMoney().add(rentMoney);
        } else {
            totalMoney = item.getRemainingMoney().add(rentMoney);
        }

        exists.setTotalMoney(totalMoney);
        exists.setRentMoney(rentMoney);

        // 🔥 AUTO COMPLETE LOGIC
        if (exists.getRemainingMoney() != null &&
            exists.getRemainingMoney().compareTo(BigDecimal.ZERO) == 0) {
            exists.setStatus("completed");
        }

        NewCustomerItems saved = itemsRepo.save(exists);

        // 🔥 Excel sync
        custItem.saveCustomerItem(saved);

        // ============================
        // ✅ DTO MAPPING
        // ============================
        dto.setId(saved.getId());
        dto.setItemName(saved.getItem_name());
        dto.setInterest(saved.getInterest());
        dto.setGiveMoney(saved.getGiveMoney());
        dto.setMonths(durationStr);
        dto.setRentMoney(rentMoney);
        dto.setTotalMoney(totalMoney);

        dto.setName(saved.getNewCustomer().getName());
        dto.setAddress(saved.getNewCustomer().getAddress());
        dto.setMobileNumber(saved.getNewCustomer().getMobileNumber());

        dto.setDate(customDate != null ? customDate : createdDate);
        dto.setRemaningAmount(saved.getTotalMoney());
        dto.setStatus(saved.getStatus());
        return dto;
    }
}