package com.api.test;

import static com.api.utils.DateTimeUtil.*;
import static com.api.utils.JavaUtils.getRandomNumber;
import static io.restassured.RestAssured.*;

import com.api.constant.*;
import com.api.records.model.*;

import static com.api.utils.SpecUtil.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

import io.qameta.allure.*;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dnyaneshwar Ubale
 */

@Listeners(com.listeners.APITestListener.class)
@Epic("User Management")
@Feature("User Creation")
public class CreateJobAPITest {

    CreateJobPayload createJobPayload;

    @BeforeTest(description = "Creating create job API Payload")
    public void setup() {
        String imeiNumber = getRandomNumber(14);
        Customer customer = new Customer("Dnyan", "Ubale", "7878787878", "", "xyz@gmail.com", "");
        CustomerAddress customerAddress = new CustomerAddress("c 304", "Shanti Sadan", "Ramdas Road",
                "near Bhavin school", "Thaltej", "4255252", "India", "Gujrat");
        CustomerProduct customerProduct = new CustomerProduct(getTimeWithDaysAgo(10), "11794514993682", imeiNumber,
                imeiNumber, getTimeWithDaysAgo(10), Product.NEXUS_2.getCode(), Model.NEXUS_2_BLUE.getCode());
        Problems problems = new Problems(problem.SMARTPHONE_IS_RUNNING_SLOW.getCode(), "Battery Issue");

        List<Problems> problemsList = new ArrayList<>();
        problemsList.add(problems);

        createJobPayload = new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getCode(), Platform.FRONT_DESK.getCode(), Warrantly_Status.IN_WARRANTY.getCode(), OEM.GOOGLE.getCode(), customer,
                customerAddress, customerProduct, problemsList);
    }

    @Story("FD Should be able to create job")
    @Description("Verify if FD is able to use create job API and Inwarranty job is created")
    @Severity(SeverityLevel.BLOCKER)
    @Test(description = "Verify if the create API is able to create Inwarranty job", groups = {"api", "smoke", "regression"})
    public void createJobAPITest() {

        given().spec(requestSpecWithAuth(Role.FD, createJobPayload))
                .when().post("job/create")
                .then()
                .spec(responseSpec_OK())
                .body("message", Matchers.equalTo("Job created successfully. "))
                .body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
                .body("data.mst_service_location_id", Matchers.equalTo(1))
                .body("data.job_number", Matchers.startsWith("JOB_"))
                .body("data.id", Matchers.notNullValue());
    }
}