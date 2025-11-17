package com.api.test;

import static io.restassured.RestAssured.*;

import static com.api.utils.ConfigManager.*;

import com.api.constant.Role;
import com.api.utils.AuthTokenProvider;
import io.restassured.http.Header;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

public class CountAPITest {

    @Test
    public void verifyCountAPIResponse(){

        Header countHeader = new Header("Authorization", AuthTokenProvider.getToken(Role.FD));

        given().baseUri(getProperty("BASE_URI"))
                .header(countHeader)
                .when().get("/dashboard/count")
                .then().log().all()
                .statusCode(200)
                .body("message", equalTo("Success"))
                .time(lessThan(1000L))
                .body("data", notNullValue())
                .body("data.size()", equalTo(3))
                .body("data.count",everyItem(greaterThanOrEqualTo(0)))
                .body("data.label",everyItem(not(blankOrNullString())))
                .body(matchesJsonSchemaInClasspath("response-schema/CountAPIRequestSchema-FD.json"))
                .body("data.key",containsInAnyOrder("pending_for_delivery","created_today","pending_fst_assignment"));
    }

    @Test
    public void countAPITest_MissingAuthToken() {
        given().baseUri(getProperty("BASE_URI"))
                .when().get("/dashboard/count")
                .then().log().all()
                .statusCode(401);
    }
}
