package com.database.dao;

import com.database.DataBaseManager;
import com.database.model.CustomerDBModel;
import io.qameta.allure.Step;

import java.sql.*;

public class CustomerDao {

    //Executing the query for the tr_Customer table..!!

    private static final String CUSTOMER_DETAILS_QUERY =
            """
                    Select * from tr_customer Where id = ?
                    """;

    @Step("Retriving the Customer Information from DB for specific customer id")
    public static CustomerDBModel getCustomerInfo(int customerId) {
        Connection conn = null;
        CustomerDBModel customerDBModel = null;
        try {
            conn = DataBaseManager.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(CUSTOMER_DETAILS_QUERY);
            preparedStatement.setInt(1, customerId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                customerDBModel = new CustomerDBModel(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("mobile_number"),
                        resultSet.getString("mobile_number_alt"),
                        resultSet.getString("email_id"),
                        resultSet.getString("email_id_alt"),
                        resultSet.getInt("tr_customer_address_id"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return customerDBModel;
    }
}
