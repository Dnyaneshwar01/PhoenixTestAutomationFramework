package com.api.utils;

import com.api.records.model.*;
import com.dataproviders.api.bean.CreateJobBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class CreateJobBeanMapper {
    private static final Logger LOGGER = LogManager.getLogger(CreateJobBeanMapper.class);

    private CreateJobBeanMapper() {
    }

    public static CreateJobPayload mapper(CreateJobBean bean) {
        LOGGER.info("Converting the Create Job bean {} to CreateJobPayload....", bean);

        int mstServiceLocationId = Integer.parseInt(bean.getMst_service_location_id());
        int mstPlatformId = Integer.parseInt(bean.getMst_platform_id());
        int mstWarrentyStatusId = Integer.parseInt(bean.getMst_warrenty_status_id());
        int oemId = Integer.parseInt(bean.getMst_oem_id());

        Customer customer = new Customer(bean.getCustomer__first_name(),
                bean.getCustomer__last_name(),
                bean.getCustomer__mobile_number(),
                bean.getCustomer__mobile_number_alt(),
                bean.getCustomer__email_id(),
                bean.getCustomer__email_id_alt());

        CustomerAddress customerAddress = new CustomerAddress(bean.getCustomer_address__flat_number(),
                bean.getCustomer_address__apartment_name(),
                bean.getCustomer_address__street_name(),
                bean.getCustomer_address__landmark(),
                bean.getCustomer_address__area(),
                bean.getCustomer_address__pincode(),
                bean.getCustomer_address__country(),
                bean.getCustomer_address__state());

        int productId = Integer.parseInt(bean.getCustomer_product__product_id());

        int modelId = Integer.parseInt(bean.getCustomer_product__mst_model_id());

        CustomerProduct customerProduct = new CustomerProduct(bean.getCustomer_product__dop(),
                bean.getCustomer_product__serial_number(),
                bean.getCustomer_product__imei1(),
                bean.getCustomer_product__imei2(),
                bean.getCustomer_product__popurl(),
                productId, modelId);

        List<Problems> problemsList = new ArrayList<Problems>();

        int problemId = Integer.parseInt(bean.getProblems__id());
        Problems problems = new Problems(problemId, bean.getProblems__remark());
        problemsList.add(problems);

        CreateJobPayload payload = new CreateJobPayload(mstServiceLocationId, mstPlatformId, mstWarrentyStatusId, oemId,
                customer, customerAddress, customerProduct, problemsList);
        LOGGER.info("Converted the bean {} to CreateJobPayload", payload);
        return payload;
    }
}
