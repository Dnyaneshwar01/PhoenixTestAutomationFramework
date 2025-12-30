package com.api.utils;

import com.api.records.model.*;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class FakerDemo2 {

    private static final String COUNTRY = "India";
    public static void main(String[] args) {

        //Create Job API payload
        //I want to create a fake customer object

        Faker faker = new Faker(new Locale("en-IND"));  //Help me create India specific data

        String fName = faker.name().firstName();
        String lName = faker.name().lastName();
        String mobileNumber = faker.numerify("86########");
        String alternalMobileNumber = faker.numerify("86########");
        String customerEmailAddress = faker.internet().emailAddress();
        String alternativeCustomerEmailAddress = faker.internet().emailAddress();

        Customer customer = new Customer(fName,lName,mobileNumber,alternalMobileNumber,customerEmailAddress,alternativeCustomerEmailAddress);

        System.out.println(customer);

        String flatNumber = faker.numerify("1##");
        String apartmentName = faker.address().streetName();
        String streetName = faker.address().streetName();
        String landmark = faker.address().streetName();
        String area = faker.address().streetName();
        String pinCode = faker.numerify("######");
        String state = faker.address().state();


        CustomerAddress customerAddress = new CustomerAddress(flatNumber,apartmentName,streetName,landmark,area,pinCode,COUNTRY,state);
        System.out.println(customerAddress);


        //CustomerProduct Fake Object

        String dop = DateTimeUtil.getTimeWithDaysAgo(10);
        String imeiSerialNumber = faker.numerify("##############");
        String popUrl = faker.internet().url();
        CustomerProduct customerProduct = new CustomerProduct(dop,imeiSerialNumber,imeiSerialNumber,imeiSerialNumber,popUrl,1,1);

        System.out.println(customerProduct);


        String fakeRemark = faker.lorem().sentence(5);

        Random random = new Random();
        int problemId = random.nextInt(26)+1;
        Problems problems = new Problems(problemId, fakeRemark);

        System.out.println(problems);

        List<Problems> problemsList = new ArrayList<Problems>();
        problemsList.add(problems);

        CreateJobPayload createJobPayload = new CreateJobPayload(0,2,1,1,customer,customerAddress,customerProduct,problemsList);

        System.out.println(createJobPayload);


    }
}
