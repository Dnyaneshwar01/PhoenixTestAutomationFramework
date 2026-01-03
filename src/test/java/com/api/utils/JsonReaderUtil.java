package com.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class JsonReaderUtil {

    public static <T> Iterator<T> loadJSON(String fileName, Class<T[]> clazz) {

        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        ObjectMapper objectMapper = new ObjectMapper();
        T[] classArray;
        List<T> list = null;
        try {
            classArray = objectMapper.readValue(inputStream,clazz);
            list =  Arrays.asList(classArray);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list.iterator();
    }
}
