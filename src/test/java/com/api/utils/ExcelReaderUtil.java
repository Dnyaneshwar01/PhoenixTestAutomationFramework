package com.api.utils;

import com.poiji.bind.Poiji;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

/**
 * @author Dnyaneshwar Ubale
 */
public class ExcelReaderUtil {

    private ExcelReaderUtil() {

    }
    public static <T> Iterator<T> loadTestData(String sheetName , Class<T> clazz) {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("testData/PhoenixTestData.xlsx");
        XSSFWorkbook myWorkBook = null;
        try {
            myWorkBook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        XSSFSheet mySheet = myWorkBook.getSheet(sheetName);

        List<T> dataList =  Poiji.fromExcel(mySheet, clazz);
        return dataList.iterator();
    }
}