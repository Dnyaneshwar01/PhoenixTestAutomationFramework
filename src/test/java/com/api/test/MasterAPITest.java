package com.api.test;

import static com.api.utils.ConfigManager.*;
import static com.api.utils.SpecUtil.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import org.testng.annotations.Test;

import static com.api.constant.Role.*;
import static io.restassured.RestAssured.*;

public class MasterAPITest {


    @Test(description = "Verify if the master API is giving correct response", groups = {"api","smoke", "regression"})
    public void masterAPITest() {

        given().spec(requestSpecWithAuth(FD))
                .when().post("master")
                .then().spec(responseSpec_OK())
                .body("message",equalTo("Success"))
                .body("data",notNullValue())
                .body("data", hasKey("mst_oem"))
                .body("data",hasKey("mst_model"))
                .body("$",hasKey("data"))
                .body("data.mst_oem.size()", equalTo(2))
                .body("data.mst_model.size()", greaterThan(0))
                .body("data.mst_oem.id", everyItem(notNullValue()))
                .body("data.mst_oem.name",everyItem(notNullValue()))
                .body(matchesJsonSchemaInClasspath("response-schema/MasterAPIResponseSchema.json"));
    }

    @Test (description = "Verify if the master API gives correct status code for invalid Auth Token", groups = {"api","negative","smoke", "regression"})
    public void invalidTokenMasterAPITest()
    {
        given().baseUri(getProperty("BASE_URI")).header("Authorization","ihjuyuyfd")
                .contentType("")
                .when().post("master")
                .then().log().all()
                .statusCode(500);
    }

    @Test (description = "Verify if the master API gives correct status code for NO Auth Token", groups = {"api","negative","smoke", "regression"})
    public void verifyMasterAPIWithoutToken(){
        given().baseUri(getProperty("BASE_URI")).header("Authorization","")
                .contentType("")
                .when().post("master")
                .then().log().all()
                .statusCode(401);
    }
}

