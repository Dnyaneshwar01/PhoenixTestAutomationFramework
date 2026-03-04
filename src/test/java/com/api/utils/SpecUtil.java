package com.api.utils;

import com.api.constant.Role;
import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;

import static com.api.utils.ConfigManager.*;

/**
 * @author Dnyaneshwar Ubale
 */
public class SpecUtil {

    //GET_DELETE
    @Step("Setting up the baseURI, Content Type as Application/JSON and attaching the sensitiveData Filter")
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
    @Step("Setting up the baseURI, Content Type as Application/JSON and attaching the sensitiveData filter ")
    public static RequestSpecification requestSpec(Object payload) {
        RequestSpecification requestSpecification =  new RequestSpecBuilder()
                .setBaseUri(getProperty("BASE_URI"))
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .setBody(payload)
                .log(LogDetail.URI)
                .log(LogDetail.METHOD)
                .log(LogDetail.HEADERS)
                .log(LogDetail.BODY)
                .build();
        return requestSpecification;
    }

    @Step("Setting up the baseURI, Content Type as Application/JSON and attaching the sensitiveData filter for role")
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
    @Step("Setting up the baseURI, Content Type as Application/JSON and attaching the sensitiveData filter for role and attching payload")
    public static RequestSpecification requestSpecWithAuth(Role role, Object payload){
        RequestSpecification request =  new RequestSpecBuilder()
                .setBaseUri(ConfigManager.getProperty("BASE_URI"))
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addHeader("Authorization", AuthTokenProvider.getToken(role))
                .setBody(payload)
                .log(LogDetail.URI)
                .log(LogDetail.METHOD)
                .log(LogDetail.HEADERS)
                .log(LogDetail.BODY)
                .build();
        return request;
    }


    @Step("Expecting the response to have contain type as Application/JSON, Status 200 and Response Time less than 1000ms ")
    public static ResponseSpecification responseSpec_OK() {

        ResponseSpecification responseSpecification =  new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .expectResponseTime(Matchers.lessThan(1000L))
                .log(LogDetail.ALL)
                .build();
        return responseSpecification;
    }

    @Step("Expecting the response to have contain type as Application/JSON and Response Time less than 1000ms and status code")
    public static ResponseSpecification responseSpec_JSON(int statusCode){
        ResponseSpecification responseSpecification =  new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(statusCode)
                .expectResponseTime(Matchers.lessThan(1000L))
                .log(LogDetail.ALL)
                .build();
        return responseSpecification;
    }

    @Step("Expecting the response to have contain type as Text and Response Time less than 1000ms and status code")
    public static ResponseSpecification responseSpec_TEXT(int statusCode){
        ResponseSpecification responseSpecification =  new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .expectResponseTime(Matchers.lessThan(1000L))
                .log(LogDetail.ALL)
                .build();
        return responseSpecification;
    }

}
