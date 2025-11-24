package com.api.test;

import static com.api.utils.DateTimeUtil.*;
import static io.restassured.RestAssured. *;

import com.api.constant.Role;
import com.api.records.model.*;
import com.api.utils.SpecUtil;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class CreateJonAPITest {

    @Test
    public void createJobAPITest() {

        Customer customer = new Customer("Dnyan", "Ubale","7878787878","","xyz@gmail.com","");
        CustomerAddress customerAddress = new CustomerAddress("c 304","Shanti Sadan","Ramdas Road",
                "near Bhavin school","Thaltej", "4255252","India","Gujrat");
        CustomerProduct customerProduct = new CustomerProduct(getTimeWithDaysAgo(10),"11770514993682","18774419973682",
                "130771979163582",getTimeWithDaysAgo(10),1,1);
        Problems problems = new Problems(1, "Battery Issue");

        List<Problems> problemsList = new ArrayList<>();
        problemsList.add(problems);

        CreateJobPayload createJobPayload = new CreateJobPayload(0,2,1,1,customer,
                customerAddress,customerProduct,problemsList);

        given().spec(SpecUtil.requestSpecWithAuth(Role.FD,createJobPayload))
                .when().post("job/create")
                .then()
                .spec(SpecUtil.responseSpec_OK())
                .body("message",Matchers.equalTo("Job created successfully. "))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
                .body("data.mst_service_location_id", Matchers.equalTo(1))
                .body("data.job_number", Matchers.startsWith("JOB_"))
                .body("data.id", Matchers.notNullValue());
    }
}