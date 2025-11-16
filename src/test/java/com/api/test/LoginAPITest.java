package com.api.test;

import com.api.pojo.UserCredentials;

import static com.api.utils.ConfigManager.*;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;

public class LoginAPITest {

    @Test
    public void loginAPITest() {

        UserCredentials userCredentials = new UserCredentials("iamfd", "password");

        given().baseUri(getProperty("BASE_URI"))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON).body(userCredentials)
                .log().uri().log().method().log().headers().log().body()
                .when().post("login")
                .then().log().all()
                .statusCode(200)
                .time(lessThan(2000L))
                .body("message",equalTo("Success"))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"))
                .extract().response();
    }
}
