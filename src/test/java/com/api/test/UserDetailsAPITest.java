package com.api.test;
import static com.api.constant.Role.*;

import com.api.utils.SpecUtil;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class UserDetailsAPITest {

    @Test
    public void userDetailsAPITest(){

        given()
                .spec(SpecUtil.requestSpecWithAuth(FD))
                .when().get("userdetails")
                .then()
                .spec(SpecUtil.responseSpec_OK())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"));
    }
}
