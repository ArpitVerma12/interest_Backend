package com.backend.services;

import com.backend.entity.NewCustomer;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class ExcelCustomersService {

    private static final String BASE_PATH = "C:\\Users\\varpi\\Desktop\\GIRVI_DATA";

    public void saveCustomerToExcel(NewCustomer user) {
        try {
            
            String filePath = BASE_PATH + "/customers.xlsx";

            // 🔥 Create folder if not exists
          //  Files.createDirectories(Paths.get(folderPath));

            Workbook workbook;
            Sheet sheet;

            File file = new File(filePath);

            if (file.exists()) {
                // Load existing file
                FileInputStream fis = new FileInputStream(file);
                workbook = new XSSFWorkbook(fis);
                sheet = workbook.getSheetAt(0);
            } else {
                // Create new file
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("Customers");

                // Create header
                Row header = sheet.createRow(0);
                header.createCell(4).setCellValue("Address");
                header.createCell(1).setCellValue("Name");
                header.createCell(2).setCellValue("EmailId");
                header.createCell(3).setCellValue("MobileNumber");
                header.createCell(0).setCellValue("UserId");
                header.createCell(5).setCellValue("Village");
                header.createCell(6).setCellValue("Remark");
            }

            int lastRow = sheet.getLastRowNum() + 1;

            Row row = sheet.createRow(lastRow);
            row.createCell(0).setCellValue(user.getUser_id());
            row.createCell(1).setCellValue(user.getName());
            row.createCell(2).setCellValue(user.getEmailId());
            row.createCell(3).setCellValue(user.getMobileNumber());
            row.createCell(4).setCellValue(user.getAddress());
            row.createCell(5).setCellValue(user.getVillage());
            row.createCell(6).setCellValue(user.getRemark());
            // Save file
            FileOutputStream fos = new FileOutputStream(filePath);
            workbook.write(fos);

            fos.close();
            workbook.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}