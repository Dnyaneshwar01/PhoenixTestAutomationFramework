package com.api.test;

import com.api.constant.*;
import com.api.records.model.*;
import com.api.response.model.CreateJobResponseModel;
import com.api.utils.JavaUtils;
import com.database.dao.CustomerAddressDao;
import com.database.dao.CustomerDao;
import com.database.dao.CustomerProductDao;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import com.database.model.CustomerProductDBModel;
import io.restassured.response.Response;
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
    CustomerProduct customerProduct;

    @BeforeTest(description = "Creating create job API Payload")
    public void setup() {
        String imeiNo = JavaUtils.getRandomNumber(14);
        customer = new Customer("Dnyaneshwar", "Sharma", "7878787878", "78787878787", "sharmauser01@gmail.com", "");
        customerAddress = new CustomerAddress("c 304", "Shanti Sadan", "Ramdas Road",
                "near Bhavin school", "Thaltej", "4255252", "India", "Gujrat");
        customerProduct = new CustomerProduct(getTimeWithDaysAgo(10), imeiNo, imeiNo, imeiNo, getTimeWithDaysAgo(10), Product.NEXUS_2.getCode(), Model.NEXUS_2_BLUE.getCode());
        Problems problems = new Problems(problem.SMARTPHONE_IS_RUNNING_SLOW.getCode(), "Battery Issue");

        List<Problems> problemsList = new ArrayList<>();
        problemsList.add(problems);

        createJobPayload = new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getCode(), Platform.FRONT_DESK.getCode(), Warrantly_Status.IN_WARRANTY.getCode(), OEM.GOOGLE.getCode(), customer,
                customerAddress, customerProduct, problemsList);
    }

    @Test(description = "Verify if the create API is able to create Inwarranty job", groups = {"api", "smoke", "regression"})
    public void createJobAPITest() {

        CreateJobResponseModel createJobResponseModel = given().spec(requestSpecWithAuth(Role.FD, createJobPayload))
                .when().post("job/create")
                .then()
                .spec(responseSpec_OK())
                .body("message", Matchers.equalTo("Job created successfully. "))
                .body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
                .body("data.mst_service_location_id", Matchers.equalTo(1))
                .body("data.job_number", Matchers.startsWith("JOB_"))
                .body("data.id", Matchers.notNullValue())
                .extract().as(CreateJobResponseModel.class);

        System.out.println("--------------------------------------------");
        int customerId = createJobResponseModel.getData().getTr_customer_id();
        System.out.println("Customer ID: " + customerId);

        CustomerDBModel customerDateFromDBModel = CustomerDao.getCustomerInfo(customerId);

        Assert.assertEquals(customer.first_name(), customerDateFromDBModel.getFirst_name(), "First name mismatch between API and DB");
        Assert.assertEquals(customer.last_name(), customerDateFromDBModel.getLast_name(), "Last name mismatch between API and DB");
        Assert.assertEquals(customer.mobile_number(), customerDateFromDBModel.getMobile_number(), "Mobile number mismatch between API and DB");
        Assert.assertEquals(customer.email_id(), customerDateFromDBModel.getEmail_id(), "Primary email mismatch between API and DB");
        Assert.assertEquals(customer.email_id_alt(), customerDateFromDBModel.getEmail_id_alt(), "Alternate email mismatch between API and DB");

        CustomerAddressDBModel customerAddressFromDB = CustomerAddressDao.getCustomerAddressData(customerDateFromDBModel.getTr_customer_address_id());
        Assert.assertEquals(customerAddressFromDB.getFlat_number(), customerAddress.flat_number(), "Flat number mismatch in customer address");
        Assert.assertEquals(customerAddressFromDB.getApartment_name(), customerAddress.apartment_name(), "Apartment name mismatch in customer address");
        Assert.assertEquals(customerAddressFromDB.getArea(), customerAddress.area(), "Area mismatch in customer address");
        Assert.assertEquals(customerAddressFromDB.getState(), customerAddress.state(), "State mismatch in customer address");
        Assert.assertEquals(customerAddressFromDB.getStreet_name(), customerAddress.street_name(), "Street name mismatch in customer address");
        Assert.assertEquals(customerAddressFromDB.getPincode(), customerAddress.pincode(), "Pincode mismatch in customer address");

        System.out.println("-------------------------------------------------------------");

        int productId = createJobResponseModel.getData().getTr_customer_product_id();
        System.out.println("productId: " + productId);

        CustomerProductDBModel customerProductDBModel = CustomerProductDao.getCustomerProductData(productId);

        Assert.assertEquals(customerProductDBModel.getImei1(), customerProduct.imei1(), "IMEI1 mismatch in customer product");
        Assert.assertEquals(customerProductDBModel.getImei2(), customerProduct.imei2(), "IMEI2 mismatch in customer product");
        Assert.assertEquals(customerProductDBModel.getSerial_number(), customerProduct.serial_number(), "Serial number mismatch in customer product");
        Assert.assertTrue(customerProduct.dop().contains(customerProductDBModel.getDop()), "Date of purchase mismatch in customer product");
        Assert.assertEquals(customerProductDBModel.getMst_model_id(), customerProduct.mst_model_id(), "Model ID mismatch in customer product");

    }
}