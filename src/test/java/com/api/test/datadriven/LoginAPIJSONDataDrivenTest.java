package com.api.test.datadriven;

import com.api.records.model.UserCredentials;
import com.api.service.AuthService;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.requestSpec;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author Dnyaneshwar Ubale
 */

public class LoginAPIJSONDataDrivenTest {

    AuthService authService;

    @BeforeMethod(description = "Initializing the Auth Service")
    public void setup() {
        authService = new AuthService();
    }

    @Test(description = "Verifying if login is working for FD user",
            groups = {"api", "regression", "Datadriven"},
            dataProviderClass = com.dataproviders.DataProviderUtils.class,
            dataProvider = "LoginAPIJSONDataProvider"
    )
    public void loginAPITest(UserCredentials userCredentials) {

        authService.login(userCredentials).then()
                .spec(responseSpec_OK())
                .body("message", equalTo("Success"))
                .body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));
    }
}
