package com.api.test;

import com.api.constant.*;
import com.api.records.model.*;
import com.api.utils.DateTimeUtil;
import com.github.javafaker.Faker;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static com.api.utils.DateTimeUtil.getTimeWithDaysAgo;
import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

/**
 * @author  Dnyaneshwar Ubale
 */
public class CreateJobAPITest2 {

    CreateJobPayload createJobPayload;
    private static final String COUNTRY = "India";

    @BeforeTest(description = "Creating create job API Payload")
    public void setup(){

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

        createJobPayload = new CreateJobPayload(0,2,1,1,
                customer,customerAddress,customerProduct,problemsList);

    }

    @Test(description = "Verify if the create API is able to create Inwarranty job", groups = {"api","smoke", "regression"})
    public void createJobAPITest() {

        given().spec(requestSpecWithAuth(Role.FD,createJobPayload))
                .when().post("job/create")
                .then()
                .spec(responseSpec_OK())
                .body("message",Matchers.equalTo("Job created successfully. "))
                .body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
                .body("data.mst_service_location_id", Matchers.equalTo(1))
                .body("data.job_number", Matchers.startsWith("JOB_"))
                .body("data.id", Matchers.notNullValue());
    }
}