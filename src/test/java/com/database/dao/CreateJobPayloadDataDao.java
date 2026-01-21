package com.database.dao;

import com.database.DataBaseManager;
import com.dataproviders.api.bean.CreateJobBean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CreateJobPayloadDataDao {

    private static final String SQL_QUERY =
            """
                    select
                         mst_service_location_id,
                         mst_platform_id,
                         mst_warrenty_status_id,
                         mst_oem_id,
                         first_name,
                         last_name,
                         mobile_number,
                         mobile_number_alt,
                         email_id,
                         email_id_alt,
                         flat_number,
                         apartment_name,
                         street_name,
                         landmark,
                         area,
                         pincode,
                         country,
                         state,
                         serial_number,
                         imei1,
                         imei2,
                         popurl,
                         dop,
                         mst_model_id,
                         mst_problem_id,
                         remark
                    
                         from tr_customer
                         INNER JOIN tr_customer_address
                         on tr_customer.tr_customer_address_id = tr_customer_address.id\s
                    
                         INNER JOIN tr_customer_product
                         on tr_customer_product.tr_customer_id = tr_customer.id
                    
                         INNER JOIN tr_job_head
                         on tr_job_head.tr_customer_id = tr_customer.id
                    
                         INNER JOIN map_job_problem
                         on map_job_problem.tr_job_head_id = tr_job_head.id
                         LIMIT 5; 
                    """;

    public static List<CreateJobBean> getCreateJobPayloadData() {
        Connection connection = null;
        Statement statement;
        ResultSet resultSet = null;

        List<CreateJobBean> beanList = new ArrayList<CreateJobBean>();
        try {
            connection = DataBaseManager.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_QUERY);

            while (resultSet.next()) {
                CreateJobBean createJobBean = new CreateJobBean();
                createJobBean.setMst_service_location_id(resultSet.getString("mst_service_location_id"));
                createJobBean.setMst_platform_id(resultSet.getString("mst_platform_id"));
                createJobBean.setMst_warrenty_status_id(resultSet.getString("mst_warrenty_status_id"));
                createJobBean.setMst_oem_id(resultSet.getString("mst_oem_id"));
                createJobBean.setCustomer__first_name(resultSet.getString("first_Name"));
                createJobBean.setCustomer__last_name(resultSet.getString("last_Name"));
                createJobBean.setCustomer__mobile_number(resultSet.getString("mobile_number"));
                createJobBean.setCustomer__mobile_number_alt(resultSet.getString("mobile_number_alt"));
                createJobBean.setCustomer__email_id(resultSet.getString("email_id"));
                createJobBean.setCustomer__email_id_alt(resultSet.getString("email_id_alt"));
                createJobBean.setCustomer_address__flat_number(resultSet.getString("flat_number"));
                createJobBean.setCustomer_address__apartment_name(resultSet.getString("apartment_name"));
                createJobBean.setCustomer_address__street_name(resultSet.getString("street_name"));
                createJobBean.setCustomer_address__landmark(resultSet.getString("landmark"));
                createJobBean.setCustomer_address__area(resultSet.getString("area"));
                createJobBean.setCustomer_address__pincode(resultSet.getString("pincode"));
                createJobBean.setCustomer_address__country(resultSet.getString("country"));
                createJobBean.setCustomer_address__state(resultSet.getString("state"));
                createJobBean.setCustomer_product__serial_number(resultSet.getString("serial_number"));
                createJobBean.setCustomer_product__imei1(resultSet.getString("imei1"));
                createJobBean.setCustomer_product__imei2(resultSet.getString("imei2"));
                createJobBean.setCustomer_product__popurl(resultSet.getString("popurl"));
                createJobBean.setCustomer_product__dop(resultSet.getString("dop"));
                createJobBean.setCustomer_product__mst_model_id(resultSet.getString("mst_model_id"));
                createJobBean.setProblems__id(resultSet.getString("mst_problem_id"));
                createJobBean.setProblems__remark(resultSet.getString("remark"));
                createJobBean.setCustomer_product__product_id("1");

                beanList.add(createJobBean);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return beanList;
    }
}
