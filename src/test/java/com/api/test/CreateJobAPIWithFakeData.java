package com.api.test;

import com.api.constant.*;
import com.api.records.model.*;
import com.api.utils.FakerDataGenerator;
import com.database.dao.CustomerAddressDao;
import com.database.dao.CustomerDao;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

/**
 * @author Dnyaneshwar Ubale
 */
public class CreateJobAPIWithFakeData {

    private CreateJobPayload createJobPayload;
    private static final String COUNTRY = "India";

    @BeforeTest(description = "Creating create job API Payload")
    public void setup() {
        createJobPayload = FakerDataGenerator.generateFakeCreateJobData();
    }

    @Test(description = "Verify if the create API is able to create Inwarranty job", groups = {"api", "smoke", "regression"})
    public void createJobAPITest() {

        int customerId = given().spec(requestSpecWithAuth(Role.FD, createJobPayload))
                .when().post("job/create")
                .then()
                .spec(responseSpec_OK())
                .body("message", Matchers.equalTo("Job created successfully. "))
                .body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
                .body("data.mst_service_location_id", Matchers.equalTo(1))
                .body("data.job_number", Matchers.startsWith("JOB_"))
                .body("data.id", Matchers.notNullValue())
                .extract().body().jsonPath().getInt("data.tr_customer_id");

        Customer expectedCustomerData = createJobPayload.customer();
        CustomerDBModel actualCustomerDataInDB = CustomerDao.getCustomerInfo(customerId);

        Assert.assertEquals(actualCustomerDataInDB.getFirst_name(), expectedCustomerData.first_name(), "First name mismatch between DB and expected data");
        Assert.assertEquals(actualCustomerDataInDB.getLast_name(), expectedCustomerData.last_name(), "Last name mismatch between DB and expected data");
        Assert.assertEquals(actualCustomerDataInDB.getMobile_number(), expectedCustomerData.mobile_number(), "Mobile number mismatch between DB and expected data");
        Assert.assertEquals(actualCustomerDataInDB.getEmail_id(), expectedCustomerData.email_id(), "Primary email ID mismatch between DB and expected data");
        Assert.assertEquals(actualCustomerDataInDB.getEmail_id_alt(), expectedCustomerData.email_id_alt(), "Alternate email ID mismatch between DB and expected data");


        CustomerAddressDBModel customerAddressFromDB = CustomerAddressDao.getCustomerAddressData(actualCustomerDataInDB.getTr_customer_address_id());

        Assert.assertEquals(customerAddressFromDB.getFlat_number(), createJobPayload.customer_address().flat_number(), "Flat number mismatch between DB and expected data");
        Assert.assertEquals(customerAddressFromDB.getApartment_name(), createJobPayload.customer_address().apartment_name(), "Apartment name mismatch between DB and expected data");
        Assert.assertEquals(customerAddressFromDB.getArea(), createJobPayload.customer_address().area(), "Customer address area mistmatch between DB and expected data");
        Assert.assertEquals(customerAddressFromDB.getState(), createJobPayload.customer_address().state(), "state mistmatch between DB and expected data");
        Assert.assertEquals(customerAddressFromDB.getStreet_name(), createJobPayload.customer_address().street_name(), "Street name mistmatch between DB and expected data");
        Assert.assertEquals(customerAddressFromDB.getPincode(), createJobPayload.customer_address().pincode(), "pincode mistmatch between DB and expected data");
    }
}