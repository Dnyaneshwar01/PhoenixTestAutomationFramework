package com.api.test;

import com.api.records.model.UserCredentials;

import com.api.utils.SpecUtil;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;

public class LoginAPITest {

    @Test
    public void loginAPITest() {

        UserCredentials userCredentials = new UserCredentials("iamfd", "password");

        given()
                .spec(SpecUtil.requestSpec(userCredentials))

                .when().post("login")

                .then()
                .spec(SpecUtil.responseSpec_OK())
                .body("message",equalTo("Success"))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));
    }
}
