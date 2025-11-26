package com.api.test;

import com.api.records.model.UserCredentials;
import static com.api.utils.SpecUtil.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;

/**
 * @author Dnyaneshwar Ubale
 */

public class LoginAPITest {

    private UserCredentials userCredentials;

    @BeforeTest(description = "Create the payload for the login API")
    public void setup(){
        userCredentials = new UserCredentials("iamfd", "password");
    }

    @Test (description = "Verifying if login is working for FD user", groups = {"api","regression","smoke"})
    public void loginAPITest() {

        given()
                .spec(requestSpec(userCredentials))
                .when().post("login")
                .then()
                .spec(responseSpec_OK())
                .body("message",equalTo("Success"))
                .body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));
    }
}
