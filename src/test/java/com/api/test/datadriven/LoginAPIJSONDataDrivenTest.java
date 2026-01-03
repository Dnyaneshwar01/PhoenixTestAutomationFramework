package com.api.test.datadriven;

import com.api.records.model.UserCredentials;
import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.requestSpec;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author Dnyaneshwar Ubale
 */

public class LoginAPIJSONDataDrivenTest {

    @Test (description = "Verifying if login is working for FD user",
            groups = {"api","regression","Datadriven"},
            dataProviderClass = com.dataproviders.DataProviderUtils.class,
            dataProvider = "LoginAPIJSONDataProvider"
    )
    public void loginAPITest(UserCredentials userCredentials) {

        given()
                .spec(requestSpec(userCredentials))
                .when().post("login")
                .then()
                .spec(responseSpec_OK())
                .body("message",equalTo("Success"))
                .body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));
    }
}
