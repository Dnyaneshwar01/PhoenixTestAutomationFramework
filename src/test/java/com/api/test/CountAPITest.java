package com.api.test;

import static com.api.constant.Role.FD;
import static io.restassured.RestAssured.*;

import static com.api.utils.SpecUtil.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

public class CountAPITest {

    @Test(description = "Verify the count API is giving correct response", groups = {"api","smoke", "regression"})
    public void verifyCountAPIResponse(){
        given().spec(requestSpecWithAuth(FD))
                .when().get("/dashboard/count")
                .then().spec(responseSpec_OK())
                .body("message", equalTo("Success"))
                .body("data", notNullValue())
                .body("data.size()", equalTo(3))
                .body("data.count",everyItem(greaterThanOrEqualTo(0)))
                .body("data.label",everyItem(not(blankOrNullString())))
                .body(matchesJsonSchemaInClasspath("response-schema/CountAPIRequestSchema-FD.json"))
                .body("data.key",containsInAnyOrder("pending_for_delivery","created_today","pending_fst_assignment"));
    }

    @Test(description = "Verify count API gives correct status if Auth not provides", groups = {"api","negative","smoke", "regression"})
    public void countAPITest_MissingAuthToken() {
        given().spec(requestSpec())
                .when().get("/dashboard/count")
                .then().spec(responseSpec_TEXT(401));
    }
}