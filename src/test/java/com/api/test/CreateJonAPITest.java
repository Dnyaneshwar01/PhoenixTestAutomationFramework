package com.api.test;

import static io.restassured.RestAssured. *;

import com.api.constant.Role;
import com.api.pojo.*;
import com.api.utils.SpecUtil;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class CreateJonAPITest {

    @Test
    public void createJobAPITest() {

        Customer customer = new Customer("Dnyan", "Ubale","7878787878","xyz@gmail.com","","");
        CustomerAddress customerAddress = new CustomerAddress("c 304","Shanti Sadan","Ramdas Road",
                "near Bhavin school","Thaltej", "4255252","India","Gujrat");
        CustomerProduct customerProduct = new CustomerProduct("2025-04-06T18:30:00.000Z","13855051993682","18545199563682",
                "13045199563682","2025-04-06T18:30:00.000Z",1,1);
        Problems problems = new Problems(1, "Battery Issue");

        Problems[] problemsArray = new Problems[1];
        problemsArray[0] = problems;

        CreateJobPayload createJobPayload = new CreateJobPayload(0,2,1,1,customer, customerAddress,customerProduct,problemsArray);

        given().spec(SpecUtil.requestSpecWithAuth(Role.FD,createJobPayload))
                .when().post("job/create")
                .then().spec(SpecUtil.responseSpec_OK())
                .body("message",Matchers.equalTo("Job created successfully. "));
    }
}