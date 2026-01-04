package com.api.utils;


import org.apache.poi.xssf.usermodel.*;

import java.io.IOException;
import java.io.InputStream;

public class ExcelReaderUtil {

    public static void main(String[] args)  {

        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("testData/PhoenixTestData.xlsx");

        XSSFWorkbook xssfWorkbook = null;
        try {
            xssfWorkbook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        XSSFSheet sheet =  xssfWorkbook.getSheet("Sheet1");
        int lastRowNumber = sheet.getLastRowNum();
        XSSFRow rowNumber = sheet.getRow(0);
        int lastCellNum = rowNumber.getLastCellNum();

        for(int row =0;  row<=lastRowNumber; row++){
            for(int column = 0; column<lastCellNum; column++){
                rowNumber = sheet.getRow(row);
                XSSFCell cell = rowNumber.getCell(column);
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
}
