package com.api.test;

import static com.api.utils.ConfigManager.*;

import static com.api.utils.AuthTokenProvider.*;

import static com.api.constant.Role.*;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class UserDetailsAPITest {

    @Test
    public void userDetailsAPITest(){

        Header authHeader = new Header("Authorization",getToken(FD));

        given().baseUri(getProperty("BASE_URI"))
                .header(authHeader)
                .accept(ContentType.JSON)
                .log().uri().log().method().log().body().log().headers()
                .when().get("userdetails")
                .then().log().all().statusCode(200)
                .time(Matchers.lessThan(1000L))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"))
                .extract().response();
    }
}
