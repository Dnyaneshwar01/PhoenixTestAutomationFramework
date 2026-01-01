package com.api.test.datadriven;

import com.api.constant.Role;
import com.api.records.model.CreateJobPayload;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

/**
 * @author  Dnyaneshwar Ubale
 */
public class CreateJobAPIFakeDateDrivenTest {

    @Test(description = "Verify if the create API is able to create Inwarranty job",
            groups = {"api","dataDriven", "regression"},
            dataProviderClass = com.dataproviders.DataProviderUtils.class,
            dataProvider = "CreateJobAPIFakerDataProvider"
    )
    public void createJobAPITest(CreateJobPayload createJobPayload) {
        given().spec(requestSpecWithAuth(Role.FD,createJobPayload))
                .when().post("job/create")
                .then()
                .spec(responseSpec_OK())
                .body("message",Matchers.equalTo("Job created successfully. "))
                .body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
                .body("data.mst_service_location_id", Matchers.equalTo(1))
                .body("data.job_number", Matchers.startsWith("JOB_"))
                .body("data.id", Matchers.notNullValue());
    }
}