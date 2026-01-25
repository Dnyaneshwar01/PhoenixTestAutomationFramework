package com.api.utils;

import java.util.Random;

public class JavaUtils {

    public static String getRandomNumber(int digitCount){
        StringBuilder stringBuilder = new StringBuilder(digitCount);
        for(int i=0; i<digitCount; i++){
            stringBuilder.append((char) ('0'+ new Random().nextInt(9)+1));
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        System.out.println(getRandomNumber(10));
    }
}
