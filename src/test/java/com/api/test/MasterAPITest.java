package com.api.test;

import com.api.utils.AuthTokenProvider;
import com.api.utils.ConfigManager;
import com.api.utils.SpecUtil;
import io.restassured.http.Header;
import static org.hamcrest.Matchers.*;

import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;

import static com.api.constant.Role.*;
import static io.restassured.RestAssured.*;

public class MasterAPITest {


    @Test
    public void masterAPITest() {

        given().spec(SpecUtil.requestSpecWithAuth(FD))
                .when().post("master")
                .then().spec(SpecUtil.responseSpec_OK())
                .body("message",equalTo("Success"))
                .body("data",notNullValue())
                .body("data", hasKey("mst_oem"))
                .body("data",hasKey("mst_model"))
                .body("$",hasKey("data"))
                .body("data.mst_oem.size()", equalTo(2))
                .body("data.mst_model.size()", greaterThan(0))
                .body("data.mst_oem.id", everyItem(notNullValue()))
                .body("data.mst_oem.name",everyItem(notNullValue()))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/MasterAPIResponseSchema.json"));
    }

    @Test
    public void invalidTokenMasterAPITest()
    {
        given().baseUri(ConfigManager.getProperty("BASE_URI")).header("Authorization","ihjuyuyfd")
                .contentType("")
                .when().post("master")
                .then().log().all()
                .statusCode(500);
    }

    @Test
    public void verifyMasterAPIWithoutToken(){
        given().baseUri(ConfigManager.getProperty("BASE_URI")).header("Authorization","")
                .contentType("")
                .when().post("master")
                .then().log().all()
                .statusCode(401);
    }
}

