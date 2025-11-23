package com.api.utils;

import static com.api.constant.Role.*;

import com.api.constant.Role;
import com.api.records.model.UserCredentials;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;

import static io.restassured.RestAssured.*;

public class AuthTokenProvider {

    private AuthTokenProvider() {}

    public static String getToken(Role role) {

        UserCredentials userCredentials = null;
        if(role == FD){
            userCredentials = new UserCredentials("iamfd","password");
        }
        else if(role == SUP) {
            userCredentials = new UserCredentials("iamsup","password");
        } else if (role == ENG) {
            userCredentials = new UserCredentials("iameng","password");
        } else if (role == QC) {
            userCredentials = new UserCredentials("iamqc","password");
        }

        String token =  given().baseUri(ConfigManager.getProperty("BASE_URI"))
                .contentType(ContentType.JSON)
                .body(userCredentials)
                .when().post("login")
                .then().log().ifValidationFails()
                .statusCode(200)
                .body("message", Matchers.equalTo("Success"))
                .extract().body().jsonPath().getString("data.token");
        return token;
    }
}