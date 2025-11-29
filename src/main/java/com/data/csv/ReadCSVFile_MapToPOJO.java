package com.data.csv;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class ReadCSVFile_MapToPOJO {

    public static void main(String[] args) {

        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("testData/LoginCreds.csv");
        InputStreamReader reader = new InputStreamReader(inputStream);
        CSVReader csvReader = new CSVReader(reader);

        CsvToBean<Bean> csvToBean = new CsvToBeanBuilder(csvReader)
                .withType(Bean.class)
                .withIgnoreEmptyLine(true)
                .build();

        List<Bean> userList = csvToBean.parse();
        System.out.println(userList);


    }
}
