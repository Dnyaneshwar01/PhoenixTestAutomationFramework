package com.api.test.datadriven;

import com.dataproviders.api.bean.UserBean;
import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.requestSpec;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author Dnyaneshwar Ubale
 */

public class LoginAPIExcelDataDrivenTest {

    @Test (description = "Verifying if login is working for FD user with multiple users",
            groups = {"api","regression","dataDriven"},
            dataProviderClass = com.dataproviders.DataProviderUtils.class,
            dataProvider = "LoginAPIExcelDataProvider"
    )
    public void loginAPITest(UserBean userBean) {
        given()
                .spec(requestSpec(userBean))
                .when().post("login")
                .then()
                .spec(responseSpec_OK())
                .body("message",equalTo("Success"))
                .body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));
    }
}
