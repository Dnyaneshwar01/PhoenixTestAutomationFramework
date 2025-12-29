package com.api.utils;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

public class CSVReaderUtility {

    /*
     *  Constructor is private
     *  Static methods,
     *  Job - Help me read the csv file and Map it a Bean
     */
    private CSVReaderUtility(){

    }

    public static <T> Iterator<T> loadCSV(String pathOfCSVFile, Class<T> bean) {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(pathOfCSVFile);
        InputStreamReader reader = new InputStreamReader(inputStream);
        CSVReader csvReader = new CSVReader(reader);

        CsvToBean<T> csvToBean = new CsvToBeanBuilder(csvReader)
                .withType(bean)
                .withIgnoreEmptyLine(true)
                .build();

        List<T> list = csvToBean.parse();
        return list.iterator();
    }
}
