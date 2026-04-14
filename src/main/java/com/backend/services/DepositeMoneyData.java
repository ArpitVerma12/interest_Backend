package com.backend.services;

import com.backend.entity.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;


@Service
public class DepositeMoneyData {
 private String getString(Object val) {
    return val != null ? val.toString() : "";
    }
private double getDouble(Number val) {
    return val != null ? val.doubleValue() : 0;
}
private String formatDateTime(LocalDateTime dt) {
    return dt != null
            ? dt.format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))
            : "";
}

    public void saveDepositToExcel(DepositeMoney deposit) {

    try {
        NewCustomerItems item = deposit.getNewCustomeritems();
        NewCustomer customer = item.getNewCustomer();

        String basePath = "C:\\Users\\varpi\\Desktop\\GIRVI_DATA\\";

        File folder = new File(basePath);
        if (!folder.exists()) folder.mkdirs();

        File file = new File(folder, "deposit_data.xlsx");

        Workbook workbook;
        Sheet sheet;

        // ✅ Load or create file
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                workbook = new XSSFWorkbook(fis);
            }
            sheet = workbook.getSheetAt(0);
        } else {
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet("Deposit Data");

            // ✅ Header
            Row header = sheet.createRow(0);
            String[] columns = {
                    "Customer Name", "User ID",
                    "Item Name",
                    "Deposit Money", "Remark", "Date"
            };

            for (int i = 0; i < columns.length; i++) {
                header.createCell(i).setCellValue(columns[i]);
            }
        }

        boolean found = false;
  


        // // ✅ Update if same deposit exists (by ID)
        // for (int i = 1; i <= sheet.getLastRowNum(); i++) {
        //     Row row = sheet.getRow(i);
        //     if (row == null) continue;

        //     String existingUserId = getString(row.getCell(1));
        //     String existingItemName = getString(row.getCell(2));

        //     if (existingUserId.equals(customer.getUser_id()) &&
        //         existingItemName.equals(item.getItem_name())) {

        //         row.createCell(3).setCellValue(getDouble(deposit.getDepositeMoney()));
        //         row.createCell(4).setCellValue(getString(deposit.getRemark()));
        //         row.createCell(5).setCellValue(formatDateTime(deposit.getCreateDate()));

        //         found = true;
        //         break;
        //     }
        // }

        // ✅ Insert new row
        if (!found) {
            int rowNum = sheet.getLastRowNum() + 1;
            Row row = sheet.createRow(rowNum);

            row.createCell(0).setCellValue(getString(customer.getName()));
            row.createCell(1).setCellValue(getString(customer.getUser_id()));
            row.createCell(2).setCellValue(getString(item.getItem_name()));

            row.createCell(3).setCellValue(getDouble(deposit.getDepositeMoney()));
            row.createCell(4).setCellValue(getString(deposit.getRemark()));
            row.createCell(5).setCellValue(formatDateTime(deposit.getCreateDate()));
        }

        // ✅ Auto size
        for (int i = 0; i < 6; i++) {
            sheet.autoSizeColumn(i);
        }

        // ✅ Save file
        try (FileOutputStream fos = new FileOutputStream(file)) {
            workbook.write(fos);
        }

        workbook.close();

        System.out.println(found ? "✅ Deposit UPDATED" : "✅ Deposit INSERTED");

    } catch (Exception e) {
        e.printStackTrace();
    }
}
}