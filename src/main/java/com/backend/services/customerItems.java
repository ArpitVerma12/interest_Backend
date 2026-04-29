package com.backend.services;

import com.backend.entity.NewCustomer;
import com.backend.entity.NewCustomerItems;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class customerItems {

    private static final String BASE_PATH = "C:\\Users\\varpi\\Desktop\\GIRVI_DATA\\";

    public String saveCustomerItem(NewCustomerItems item) {

        try {
            NewCustomer customer = item.getNewCustomer();

            // ✅ Folder per customer
            // String folderName = customer.getName() + "_" + customer.getUser_id();
            File folder = new File(BASE_PATH);

            if (!folder.exists()) folder.mkdirs();

            File file = new File(folder, "customer_item_data.xlsx");

            Workbook workbook;
            Sheet sheet;

            // ✅ Safe file handling
            if (file.exists()) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    workbook = new XSSFWorkbook(fis);
                }
                sheet = workbook.getSheetAt(0);
            } else {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("Customer Data");
                createHeader(sheet);
            }

            boolean found = false;

            // ✅ Update existing row
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String userId = getCellValue(row.getCell(1));
                String itemName = getCellValue(row.getCell(2));

                if (userId.equals(customer.getUser_id()) &&
                        itemName.equals(item.getItem_name())) {

                    fillRow(row, customer, item);
                    found = true;
                    break;
                }
            }

            // ✅ Insert new row
            if (!found) {
                int rowNum = sheet.getLastRowNum() + 1;
                Row row = sheet.createRow(rowNum);
                fillRow(row, customer, item);
            }

            // ✅ Auto size columns
            for (int i = 0; i < 8; i++) {
                sheet.autoSizeColumn(i);
            }

            // ✅ Write file safely
            try (FileOutputStream fos = new FileOutputStream(file)) {
                workbook.write(fos);
            }

            workbook.close();
           return "saved successfully";
            //System.out.println(found ? "✅ UPDATED" : "✅ INSERTED");

        } catch (FileNotFoundException e) {
    throw new RuntimeException("EXCEL_OPEN");
}
catch (IOException e){
    throw new RuntimeException("EXCEL_ERROR");
}
    }

    // =========================
    // 🔧 Helper Methods
    // =========================

    private void createHeader(Sheet sheet) {
        Row header = sheet.createRow(0);

        String[] columns = {
                "Customer Name", "User ID",
                "Item Name", "Interest", "Give Money",
                "Rent Money", "Remaining Money", "Total Money",
                "Time","status","default_date","Remark","entry_date"
        };

        for (int i = 0; i < columns.length; i++) {
            header.createCell(i).setCellValue(columns[i]);
        }
    }
    //=================================
    public String getString(Object val) {
    return val != null ? val.toString() : "";
    }
//==========================================
    private void fillRow(Row row, NewCustomer customer, NewCustomerItems item) {

        row.createCell(0).setCellValue(customer.getName());
        row.createCell(1).setCellValue(customer.getUser_id());

        row.createCell(2).setCellValue(item.getItem_name());
        row.createCell(3).setCellValue(getDouble(item.getInterest()));
        row.createCell(4).setCellValue(getDouble(item.getGiveMoney()));
        row.createCell(5).setCellValue(getDouble(item.getRentMoney()));
        row.createCell(6).setCellValue(getDouble(item.getRemainingMoney()));
        row.createCell(7).setCellValue(getDouble(item.getTotalMoney()));
        row.createCell(8).setCellValue(getString(item.getTime()));                 // String
    row.createCell(9).setCellValue(getString(item.getStatus()));              // String
    row.createCell(10).setCellValue(getString(item.getCreate_at()));          // LocalDateTime → String
    row.createCell(11).setCellValue(getString(item.getRemark()));             // String
    row.createCell(12).setCellValue(getString(item.getCustomDate())); 
    }

    private double getDouble(Number val) {
        return val != null ? val.doubleValue() : 0;
    }

    private String getCellValue(Cell cell) {
        return cell != null ? cell.toString() : "";
    }
}