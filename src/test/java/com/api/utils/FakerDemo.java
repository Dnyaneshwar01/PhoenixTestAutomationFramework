package com.api.utils;

import com.github.javafaker.Faker;

import java.util.Locale;

public class FakerDemo {

    public static void main(String[] args) {

        Faker faker = new Faker(new Locale("en-IND"));
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        System.out.println(firstName + " "+ lastName);
    }
}
