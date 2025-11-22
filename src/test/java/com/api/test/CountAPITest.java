package com.api.test;

import static com.api.constant.Role.FD;
import static io.restassured.RestAssured.*;

import static com.api.utils.ConfigManager.*;

import com.api.constant.Role;
import com.api.utils.AuthTokenProvider;
import com.api.utils.SpecUtil;
import io.restassured.http.Header;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

public class CountAPITest {

    @Test
    public void verifyCountAPIResponse(){

        given().spec(SpecUtil.requestSpecWithAuth(FD))
                .when().get("/dashboard/count")
                .then().spec(SpecUtil.responseSpec_OK())
                .body("message", equalTo("Success"))
                .body("data", notNullValue())
                 .body("data.size()", equalTo(3))
                .body("data.count",everyItem(greaterThanOrEqualTo(0)))
                .body("data.label",everyItem(not(blankOrNullString())))
                .body(matchesJsonSchemaInClasspath("response-schema/CountAPIRequestSchema-FD.json"))
                .body("data.key",containsInAnyOrder("pending_for_delivery","created_today","pending_fst_assignment"));
    }

    @Test
    public void countAPITest_MissingAuthToken() {
        given().spec(SpecUtil.requestSpec())
                .when().get("/dashboard/count")
                .then().spec(SpecUtil.responseSpec_TEXT(401));
    }
}