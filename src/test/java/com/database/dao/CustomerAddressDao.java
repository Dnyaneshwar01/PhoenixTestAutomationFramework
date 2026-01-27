package com.database.dao;

import com.database.DataBaseManager;
import com.database.model.CustomerAddressDBModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerAddressDao {

    private static final String CUSTOMER_ADDRESS_QUERY =
            """
                    select id,
                           flat_number,
                           apartment_name,
                           street_name,
                           landmark,
                           area,
                           pincode,
                           country,
                           state
                           from tr_customer_address
                           where id = ?
                    """;


    private CustomerAddressDao() {

    }

    public static CustomerAddressDBModel getCustomerAddressData(int customerAddressId) {
        Connection connection;
        CustomerAddressDBModel customerAddressDBModel = null;
        try {
            connection = DataBaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CUSTOMER_ADDRESS_QUERY);
            preparedStatement.setInt(1, customerAddressId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                customerAddressDBModel = new CustomerAddressDBModel(resultSet.getInt("id"),
                        resultSet.getString("flat_number"),
                        resultSet.getString("apartment_name"),resultSet.getString("street_name"),
                        resultSet.getString("landmark"),
                        resultSet.getString("area"),
                        resultSet.getString("pincode"),
                        resultSet.getString("country"),
                        resultSet.getString("state"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return customerAddressDBModel;
    }
}
