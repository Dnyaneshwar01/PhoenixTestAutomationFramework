package com.api.test;

import com.api.records.model.UserCredentials;

import static com.api.utils.SpecUtil.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

import io.qameta.allure.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;

/**
 * @author Dnyaneshwar Ubale
 */

@Listeners(com.listeners.APITestListener.class)
@Epic("User Management")
@Feature("Authentication")
public class LoginAPITest {

    private UserCredentials userCredentials;

    @BeforeTest(description = "Create the payload for the login API")
    public void setup() {
        userCredentials = new UserCredentials("iamfd", "password");
    }

    @Story("Valid user should be able to login into the system")
    @Description("Verify if FD user is able to login via api")
    @Severity(SeverityLevel.BLOCKER)
    @Test(description = "Verifying if login is working for FD user", groups = {"api", "regression", "smoke"})
    public void loginAPITest() {

        given()
                .spec(requestSpec(userCredentials))
                .when().post("login")
                .then()
                .spec(responseSpec_OK())
                .body("message", equalTo("Success"))
                .body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));
    }
}
