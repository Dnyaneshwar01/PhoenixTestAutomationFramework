package com.api.test;

import static com.api.constant.Role.*;
import static com.api.utils.SpecUtil.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

import com.api.service.UserService;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

/**
 * @author Dnyaneshwar Ubale
 */
@Listeners(com.listeners.APITestListener.class)
@Epic("User Management")
@Feature("User Details")
public class UserDetailsAPITest {

    private UserService userService;

    @BeforeMethod(description = "Setting up the userService instance")
    public void setup() {
        userService = new UserService();
    }

    @Story("UserDetails should be shown")
    @Description("Verify if the user details API response is shown correctly")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Verify if the user details API response is shown correctly", groups = {"api", "smoke", "regression"})
    public void userDetailsAPITest() {
        userService.userDetails(FD).then()
                .spec(responseSpec_OK())
                .body(matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"));
    }
}