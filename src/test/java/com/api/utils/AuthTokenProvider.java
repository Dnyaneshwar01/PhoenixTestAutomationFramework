package com.api.utils;

import static com.api.constant.Role.*;

import com.api.constant.Role;
import com.api.records.model.UserCredentials;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matchers;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static io.restassured.RestAssured.*;

public class AuthTokenProvider {

    private static final Logger LOGGER = LogManager.getLogger(AuthTokenProvider.class);
    private static Map<Role, String> tokenCache = new ConcurrentHashMap<>();

    private AuthTokenProvider() {
    }

    @Step("Getting the Auth token for the role")
    public static String getToken(Role role) {

        LOGGER.info("Checking if the token for {} is present in the cache", role);
        if (tokenCache.containsKey(role)) {
            LOGGER.info("token fount for {} ", role);
            return tokenCache.get(role);
        }
        LOGGER.info("token Not found marking the login request for the role {}", role);

        UserCredentials userCredentials = null;
        if (role == FD) {
            userCredentials = new UserCredentials("iamfd", "password");
        } else if (role == SUP) {
            userCredentials = new UserCredentials("iamsup", "password");
        } else if (role == ENG) {
            userCredentials = new UserCredentials("iameng", "password");
        } else if (role == QC) {
            userCredentials = new UserCredentials("iamqc", "password");
        }

        String token = given().baseUri(ConfigManager.getProperty("BASE_URI"))
                .contentType(ContentType.JSON)
                .body(userCredentials)
                .when().post("login")
                .then().log().ifValidationFails()
                .statusCode(200)
                .body("message", Matchers.equalTo("Success"))
                .extract().body().jsonPath().getString("data.token");

        LOGGER.info("Token cache for future request");

        tokenCache.put(role, token);
        return token;
    }
}