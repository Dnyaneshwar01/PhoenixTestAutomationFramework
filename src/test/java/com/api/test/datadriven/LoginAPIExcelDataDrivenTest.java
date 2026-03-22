package com.api.test.datadriven;

import com.api.service.AuthService;
import com.dataproviders.api.bean.UserBean;
import org.testng.annotations.BeforeMethod;
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

    AuthService authService;

    @BeforeMethod(description = "Initializing the Auth Service")
    public void setup() {
        authService = new AuthService();
    }

    @Test (description = "Verifying if login is working for FD user with multiple users",
            groups = {"api","regression","dataDriven"},
            dataProviderClass = com.dataproviders.DataProviderUtils.class,
            dataProvider = "LoginAPIExcelDataProvider"
    )
    public void loginAPITest(UserBean userBean) {
        authService.login(userBean).then()
                .spec(responseSpec_OK())
                .body("message",equalTo("Success"))
                .body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));
    }
}
