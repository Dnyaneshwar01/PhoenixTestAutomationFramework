package com.api.utils;

import com.poiji.bind.Poiji;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger LOGGER = LogManager.getLogger(ExcelReaderUtil.class);

    private ExcelReaderUtil() {

    }

    public static <T> Iterator<T> loadTestData(String xlsxFile, String sheetName, Class<T> clazz) {
        LOGGER.info("Reading the test data from .xlsx file {} and the sheet name is {}", xlsxFile, sheetName);

        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(xlsxFile);
        XSSFWorkbook myWorkBook = null;
        try {
            myWorkBook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        XSSFSheet mySheet = myWorkBook.getSheet(sheetName);
        LOGGER.info("Converting the XSSFSheet {} to POJO class of type {}", xlsxFile, sheetName);

        List<T> dataList = Poiji.fromExcel(mySheet, clazz);
        return dataList.iterator();
    }
}