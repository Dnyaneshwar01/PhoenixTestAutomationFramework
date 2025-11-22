package com.api.utils;

import com.api.constant.Role;
import com.api.pojo.UserCredentials;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;

import static com.api.utils.ConfigManager.*;

public class SpecUtil {

    //GET_DELETE
    public static RequestSpecification requestSpec() {
        //To take care of common request sections
        RequestSpecification request =  new RequestSpecBuilder()
                .setBaseUri(ConfigManager.getProperty("BASE_URI"))
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .log(LogDetail.URI)
                .log(LogDetail.METHOD)
                .log(LogDetail.HEADERS)
                .log(LogDetail.BODY)
                .build();
        return request;
    }


    //Post-Patch-PUT
    public static RequestSpecification requestSpec(UserCredentials userCreds) {
        RequestSpecification requestSpecification =  new RequestSpecBuilder()
                .setBaseUri(getProperty("BASE_URI"))
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .setBody(userCreds)
                .log(LogDetail.URI)
                .log(LogDetail.METHOD)
                .log(LogDetail.HEADERS)
                .log(LogDetail.BODY)
                .build();
        return requestSpecification;
    }

    public static RequestSpecification requestSpecWithAuth(Role role){
        RequestSpecification request =  new RequestSpecBuilder()
                .setBaseUri(ConfigManager.getProperty("BASE_URI"))
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addHeader("Authorization", AuthTokenProvider.getToken(role))
                .log(LogDetail.URI)
                .log(LogDetail.METHOD)
                .log(LogDetail.HEADERS)
                .log(LogDetail.BODY)
                .build();
        return request;
    }

    public static ResponseSpecification responseSpec_OK() {

        ResponseSpecification responseSpecification =  new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .expectResponseTime(Matchers.lessThan(1000L))
                .log(LogDetail.ALL)
                .build();
        return responseSpecification;
    }

    public static ResponseSpecification responseSpec_JSON(int statusCode){
        ResponseSpecification responseSpecification =  new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(statusCode)
                .expectResponseTime(Matchers.lessThan(1000L))
                .log(LogDetail.ALL)
                .build();
        return responseSpecification;
    }

    public static ResponseSpecification responseSpec_TEXT(int statusCode){
        ResponseSpecification responseSpecification =  new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .expectResponseTime(Matchers.lessThan(1000L))
                .log(LogDetail.ALL)
                .build();
        return responseSpecification;
    }

}
