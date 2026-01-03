package com.api.utils;

import com.api.records.model.*;
import com.github.javafaker.Faker;

import java.util.*;

public class FakerDataGenerator {

    private static Faker faker = new Faker(new Locale("en-IND"));
    private final static String COUNTRY = "India";
    private final static Random RANDOM = new Random();
    private final static int MST_SERVISE_LOCATION_ID = 0;
    private final static int MST_PATFORMN_ID = 2;
    private final static int MST_WARRENTY_STATUS_ID = 1;
    private final static int MST_OEM_ID = 1;
    private final static int PRODUCT_ID = 1;
    private final static int MST_MODEL_ID = 1;

    private final static int validProblemId[] = {1,2,3,4,5,6,7,8,9,10,11,12,15,16,17,19,20,22,24,26,27,28,29};

    private FakerDataGenerator() {

    }

    public static CreateJobPayload generateFakeCreateJobData() {
        Customer customer = generateFakeCustomerData();
        CustomerAddress customerAddress = generateFakeCustomerAddressData();
        CustomerProduct customerProduct = generateFakeCustomerProductData();
        List<Problems> problemsList = generateFakaProblemList();

        CreateJobPayload payload = new CreateJobPayload(MST_SERVISE_LOCATION_ID,MST_PATFORMN_ID,MST_WARRENTY_STATUS_ID,MST_OEM_ID,customer,customerAddress,customerProduct,problemsList);
        return payload;
    }

    /**
     * @param count
     * @return
     */
    public static Iterator<CreateJobPayload> generateFakeCreateJobData(int count) {

        List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();

        for(int i = 1; i<=count; i++) {
            Customer customer = generateFakeCustomerData();
            CustomerAddress customerAddress = generateFakeCustomerAddressData();
            CustomerProduct customerProduct = generateFakeCustomerProductData();
            List<Problems> problemsList = generateFakaProblemList();

            CreateJobPayload payload = new CreateJobPayload(MST_SERVISE_LOCATION_ID, MST_PATFORMN_ID, MST_WARRENTY_STATUS_ID, MST_OEM_ID, customer, customerAddress, customerProduct, problemsList);
            payloadList.add(payload);
        }
        return payloadList.iterator();
    }

    private static List<Problems> generateFakaProblemList() {

        int count = RANDOM.nextInt(3)+1;
        int randomIndex;
        String fakeRemark;
        Problems problems;
        List<Problems> problemsList = new ArrayList<Problems>();

        for (int i =1; i <= count; i++) {
            randomIndex = RANDOM.nextInt(validProblemId.length);
            fakeRemark = faker.lorem().sentence(3);
            problems = new Problems(validProblemId[randomIndex], fakeRemark);
            problemsList.add(problems);
        }
        return problemsList;
    }

    private static CustomerProduct generateFakeCustomerProductData() {
        String dop = DateTimeUtil.getTimeWithDaysAgo(10);
        String imeiSerialNumber = faker.numerify("##############");
        String popUrl = faker.internet().url();
        CustomerProduct customerProduct = new CustomerProduct(dop,imeiSerialNumber,imeiSerialNumber,imeiSerialNumber,popUrl,PRODUCT_ID,MST_MODEL_ID);
        return customerProduct;
    }

    private static CustomerAddress generateFakeCustomerAddressData() {

        String flatNumber = faker.numerify("1##");
        String apartmentName = faker.address().streetName();
        String streetName = faker.address().streetName();
        String landmark = faker.address().streetName();
        String area = faker.address().streetName();
        String pinCode = faker.numerify("######");
        String state = faker.address().state();

        CustomerAddress customerAddress = new CustomerAddress(flatNumber,apartmentName,streetName,landmark,area,pinCode,COUNTRY,state);

        return customerAddress;
    }

    private static Customer generateFakeCustomerData() {
        String fName = faker.name().firstName();
        String lName = faker.name().lastName();
        String mobileNumber = faker.numerify("86########");
        String alternalMobileNumber = faker.numerify("86########");
        String customerEmailAddress = faker.internet().emailAddress();
        String alternativeCustomerEmailAddress = faker.internet().emailAddress();

        Customer customer = new Customer(fName,lName,mobileNumber,alternalMobileNumber,customerEmailAddress,alternativeCustomerEmailAddress);

        return customer;
    }


}
