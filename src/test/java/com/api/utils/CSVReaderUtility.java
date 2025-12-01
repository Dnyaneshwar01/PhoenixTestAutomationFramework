package com.api.utils;

import com.dataProvider.api.bean.UserBean;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class CSVReaderUtility {

    /*
     *  Constructor is private
     *  Static methods,
     *  Job - Help me read the csv file and Map it a Bean
     */
    private CSVReaderUtility(){

    }

    public static void loadCSV(String pathOfCSVFile) {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("testData/LoginCreds.csv");
        InputStreamReader reader = new InputStreamReader(inputStream);
        CSVReader csvReader = new CSVReader(reader);

        CsvToBean<UserBean> csvToBean = new CsvToBeanBuilder(csvReader)
                .withType(UserBean.class)
                .withIgnoreEmptyLine(true)
                .build();

        List<UserBean> userList = csvToBean.parse();
        System.out.println(userList);
    }
}
