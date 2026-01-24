package com.api.test;

import com.api.constant.*;
import com.api.records.model.*;
import com.database.dao.CustomerAddressDao;
import com.database.dao.CustomerDao;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static com.api.utils.DateTimeUtil.getTimeWithDaysAgo;
import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

/**
 * @author Dnyaneshwar Ubale
 */
public class CreateJobAPIWithDBValidationTest {

    CreateJobPayload createJobPayload;
    Customer customer;
    CustomerAddress customerAddress;

    @BeforeTest(description = "Creating create job API Payload")
    public void setup() {
        customer = new Customer("Dnyaneshwar", "Sharma", "7878787878", "78787878787", "sharmauser01@gmail.com", "");
        customerAddress = new CustomerAddress("c 304", "Shanti Sadan", "Ramdas Road",
                "near Bhavin school", "Thaltej", "4255252", "India", "Gujrat");
        CustomerProduct customerProduct = new CustomerProduct(getTimeWithDaysAgo(10), "250779079163713", "250779079163713",
                "250779079163713", getTimeWithDaysAgo(10), Product.NEXUS_2.getCode(), Model.NEXUS_2_BLUE.getCode());
        Problems problems = new Problems(problem.SMARTPHONE_IS_RUNNING_SLOW.getCode(), "Battery Issue");

        List<Problems> problemsList = new ArrayList<>();
        problemsList.add(problems);

        createJobPayload = new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getCode(), Platform.FRONT_DESK.getCode(), Warrantly_Status.IN_WARRANTY.getCode(), OEM.GOOGLE.getCode(), customer,
                customerAddress, customerProduct, problemsList);
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

        System.out.println("--------------------------------------------");
        System.out.println(customerId);

        CustomerDBModel customerDateFromDBModel = CustomerDao.getCustomerInfo(customerId);
        System.out.println(customerDateFromDBModel);

        Assert.assertEquals(customer.first_name(),customerDateFromDBModel.getFirst_name());
        Assert.assertEquals(customer.last_name(),customerDateFromDBModel.getLast_name());
        Assert.assertEquals(customer.mobile_number(),customerDateFromDBModel.getMobile_number());
        Assert.assertEquals(customer.email_id(),customerDateFromDBModel.getEmail_id());
        Assert.assertEquals(customer.email_id_alt(),customerDateFromDBModel.getEmail_id_alt());

        System.out.println("----------------------------------------------------------");
        System.out.println(customerDateFromDBModel.getTr_customer_address_id());

        CustomerAddressDBModel customerAddressFromDB =CustomerAddressDao.getCustomerAddressData(customerDateFromDBModel.getTr_customer_address_id());

        Assert.assertEquals(customerAddressFromDB.getFlat_number(), customerAddress.flat_number());
        Assert.assertEquals(customerAddressFromDB.getApartment_name(), customerAddress.apartment_name());
        Assert.assertEquals(customerAddressFromDB.getArea(), customerAddress.area());
        Assert.assertEquals(customerAddressFromDB.getState(), customerAddress.state());
        Assert.assertEquals(customerAddressFromDB.getStreet_name(), customerAddress.street_name());
        Assert.assertEquals(customerAddressFromDB.getPincode(), customerAddress.pincode());


    }
}