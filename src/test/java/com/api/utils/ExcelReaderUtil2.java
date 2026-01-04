package com.api.utils;


import com.api.records.model.UserCredentials;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelReaderUtil2 {

    private ExcelReaderUtil2() {

    }
    public static Iterator<UserCredentials> loadTestData() {

        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("testData/PhoenixTestData.xlsx");
        XSSFWorkbook myWorkBook = null;
        try {
            myWorkBook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        XSSFSheet mySheet = myWorkBook.getSheet("LoginTestData");
        XSSFRow headerRow = mySheet.getRow(0);

        int userNameIndex = -1;
        int passwordIndex = -1;

        for(Cell cell : headerRow) {
            if(cell.getStringCellValue().trim().equalsIgnoreCase("username")){
                userNameIndex = cell.getColumnIndex();
            }
            if(cell.getStringCellValue().trim().equalsIgnoreCase("password")){
                passwordIndex = cell.getColumnIndex();
            }
        }

        int lastRowNumIndex = mySheet.getLastRowNum();
        XSSFRow rowData;
        XSSFRow myRow;
        XSSFCell myCell;
        UserCredentials userCredentials;

        List<UserCredentials> userList = new ArrayList<UserCredentials>();
        for(int rowIndex = 1; rowIndex<=lastRowNumIndex; rowIndex++){
            rowData = mySheet.getRow(rowIndex);
            userCredentials = new UserCredentials(rowData.getCell(userNameIndex).toString(), rowData.getCell(passwordIndex).toString());
            userList.add(userCredentials);
        }
        return userList.iterator();
    }
}