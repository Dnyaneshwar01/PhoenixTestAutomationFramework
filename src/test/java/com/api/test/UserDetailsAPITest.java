package com.api.test;
import static com.api.constant.Role.*;
import static com.api.utils.SpecUtil.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;


/**
 * @author Dnyaneshwar Ubale
 */

public class UserDetailsAPITest {

    @Test(description = "Verify if the user details API response is shown correctly", groups = {"api","smoke", "regression"})
    public void userDetailsAPITest(){

        given()
                .spec(requestSpecWithAuth(FD))
                .when().get("userdetails")
                .then()
                .spec(responseSpec_OK())
                .body(matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"));
    }
}
