package com.database.dao;

import com.database.DataBaseManager;
import com.database.model.JobHeadModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JobHeadDao {

    private static final String JOB_HEADER_QUERY =
            """
                    select * from tr_job_head where tr_customer_id = ? ;
                    """;

    private JobHeadDao() {

    }

    public static JobHeadModel getDataFromJobHead(int tr_customer_id) {
        JobHeadModel jobHeadModel = null;
        try {
            Connection connection = DataBaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(JOB_HEADER_QUERY);
            preparedStatement.setInt(1, tr_customer_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                jobHeadModel = new JobHeadModel(
                        resultSet.getInt("id"),
                        resultSet.getString("job_number"),
                        resultSet.getInt("tr_customer_id"),
                        resultSet.getInt("tr_customer_product_id"),
                        resultSet.getInt("mst_service_location_id"),
                        resultSet.getInt("mst_platform_id"),
                        resultSet.getInt("mst_warrenty_status_id"),
                        resultSet.getInt("mst_oem_id"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return jobHeadModel;
    }
}
