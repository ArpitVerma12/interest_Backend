package com.backend.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.backend.entity.NewCustomer;
import com.backend.entity.NewCustomerItems;
import com.backend.entity.NewCustomerWeight;

@Service
public class CustomerItemsWeightData {
    private double getDouble(Number val) {
    return val != null ? val.doubleValue() : 0;
}

private String getString(Object val) {
    return val != null ? val.toString() : "";
}
    public void saveWeightToExcel(NewCustomerWeight weightObj) {

    try {
        NewCustomerItems item = weightObj.getNewCustomerItems();
        NewCustomer customer = item.getNewCustomer();

        String basePath = "C:\\Users\\varpi\\Desktop\\GIRVI_DATA\\";
        // String folderName = customer.getName() + "_" + customer.getUser_id();

        File folder = new File(basePath);
        if (!folder.exists()) folder.mkdirs();

        File file = new File(folder, "Item_weight_data.xlsx");

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
            sheet = workbook.createSheet("Weight Data");

            // ✅ Header
            Row header = sheet.createRow(0);
            String[] columns = {
                    "User ID",
                    "Weight", "Unit"
            };

            for (int i = 0; i < columns.length; i++) {
                header.createCell(i).setCellValue(columns[i]);
            }
        }

        boolean found = false;


            int rowNum = sheet.getLastRowNum() + 1;
            Row row = sheet.createRow(rowNum);

            row.createCell(0).setCellValue(getString(customer.getUser_id()));
            // row.createCell(2).setCellValue(getString(item.getItem_name()));

            row.createCell(1).setCellValue(getDouble(weightObj.getWeight()));
            row.createCell(2).setCellValue(getString(weightObj.getUnit()));
      

        // ✅ Auto size
        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
        }

        // ✅ Save file
        try (FileOutputStream fos = new FileOutputStream(file)) {
            workbook.write(fos);
        }

        workbook.close();

        System.out.println(found ? "✅ Weight UPDATED" : "✅ Weight INSERTED");

    } catch (Exception e) {
        e.printStackTrace();
    }
}
}
