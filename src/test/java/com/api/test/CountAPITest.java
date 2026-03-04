package com.api.test;

import static com.api.constant.Role.FD;
import static io.restassured.RestAssured.*;

import static com.api.utils.SpecUtil.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static org.hamcrest.Matchers.*;

import io.qameta.allure.*;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 * @author Dnyaneshwar Ubale
 */

@Listeners(com.listeners.APITestListener.class)
@Epic("Job Management")
@Feature("Job Count")
public class CountAPITest {

    @Story("Job Count data is shown correctly")
    @Description("Verifying the count API is giving correct response")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Verify the count API is giving correct response", groups = {"api", "smoke", "regression"})
    public void verifyCountAPIResponse() {
        given().spec(requestSpecWithAuth(FD))
                .when().get("/dashboard/count")
                .then().spec(responseSpec_OK())
                .body("message", equalTo("Success"))
                .body("data", notNullValue())
                .body("data.size()", equalTo(3))
                .body("data.count", everyItem(greaterThanOrEqualTo(0)))
                .body("data.label", everyItem(not(blankOrNullString())))
                .body(matchesJsonSchemaInClasspath("response-schema/CountAPIRequestSchema-FD.json"))
                .body("data.key", containsInAnyOrder("pending_for_delivery", "created_today", "pending_fst_assignment"));
    }

    @Story("Job Count data is shown correctly")
    @Description("Verify count API gives correct status if Auth not provides")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Verify count API gives correct status if Auth not provides", groups = {"api", "negative", "smoke", "regression"})
    public void countAPITest_MissingAuthToken() {
        given().spec(requestSpec())
                .when().get("/dashboard/count")
                .then().spec(responseSpec_TEXT(401));
    }
}