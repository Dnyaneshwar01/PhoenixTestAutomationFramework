package com.database.dao;

import com.database.DataBaseManager;
import com.database.model.CustomerProductDBModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerProductDao {

    private static final String CUSTOMER_PRODUCT_QUERY =
            """
                    select id,tr_customer_id,mst_model_id,dop,popurl,imei2,imei1,serial_number
                    from tr_customer_product
                    Where id = ?;                                     
                    """;

    private CustomerProductDao() {

    }

    public static CustomerProductDBModel getCustomerProductData(int tr_customer_product_id) {

        CustomerProductDBModel customerProductDBModel = null;
        try {
            Connection connection = DataBaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CUSTOMER_PRODUCT_QUERY);
            preparedStatement.setInt(1, tr_customer_product_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                customerProductDBModel = new CustomerProductDBModel(
                        resultSet.getInt("id"),
                        resultSet.getInt("tr_customer_id"),
                        resultSet.getInt("mst_model_id"),
                        resultSet.getString("dop"),
                        resultSet.getString("popurl"),
                        resultSet.getString("imei2"),
                        resultSet.getString("imei1"),
                        resultSet.getString("serial_number")
                );
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return customerProductDBModel;
    }
}
