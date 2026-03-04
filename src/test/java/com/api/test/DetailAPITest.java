package com.api.test;

import com.api.constant.Role;
import com.api.records.model.DetailAPIKeys;
import io.qameta.allure.*;
import org.hamcrest.Matchers;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.*;
import static io.restassured.RestAssured.*;

@Listeners(com.listeners.APITestListener.class)
@Epic("Job Management")
@Feature("Job Details")
public class DetailAPITest {

    DetailAPIKeys detailAPIKeys = new DetailAPIKeys("created_today");

    @Story("Job Details is shown correcly for FD")
    @Description("Verify the created today Details API response")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Verify the created today Details API response",
            groups = {"Sanity", "api", "regression"})
    public void verifyCreatedTodayDashboardDetails() {
        given().spec(requestSpecWithAuth(Role.FD, detailAPIKeys))
                .when().post("/dashboard/details")
                .then().spec(responseSpec_OK())
                .body("message", Matchers.equalTo("Success"))
                .body("data", Matchers.notNullValue())
                .body("data.size()", Matchers.greaterThan(0));
    }
}