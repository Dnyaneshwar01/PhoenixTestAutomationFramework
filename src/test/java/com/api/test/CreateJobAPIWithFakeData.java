package com.api.test;

import com.api.constant.*;
import com.api.records.model.*;
import com.api.utils.FakerDataGenerator;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

/**
 * @author  Dnyaneshwar Ubale
 */
public class CreateJobAPIWithFakeData {

    private CreateJobPayload createJobPayload;
    private static final String COUNTRY = "India";

    @BeforeTest(description = "Creating create job API Payload")
    public void setup(){
        createJobPayload = FakerDataGenerator.generateFakeCreateJobData();
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