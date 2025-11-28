package com.data.csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.util.List;

public class ReadCSVFile {

    public static void main(String[] args) throws IOException, CsvException {
 /*
        File file = new File(System.getProperty("user.dir")+"\\PhoenixTestAutomationFramework\\src\\main\\resources\\testData\\LoginCreds.csv");
        FileReader fileReader = new FileReader(file);

  */
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("testData/LoginCreds.csv");
        InputStreamReader reader = new InputStreamReader(inputStream);

        CSVReader csvReader = new CSVReader(reader);

        List<String[]> dataList =  csvReader.readAll();

        for(String[] dataArray: dataList){

            System.out.print(dataArray[0]);  // First Col Data
            System.out.println(dataArray[1]);  // First Col Data

        }

    }
}
