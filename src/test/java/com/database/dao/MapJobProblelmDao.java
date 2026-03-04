package com.database.dao;

import com.database.DataBaseManager;
import com.database.model.MapJobProblemModel;
import io.qameta.allure.Step;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MapJobProblelmDao {

    private static final String PROBLEM_QUERY =
            """
                    select * from map_job_problem WHERE tr_job_head_id = ?;
                    """;

    @Step("Retriving the Problem details informatiopn from DB for specific job head id")
    public static MapJobProblemModel getProblemDetails(int tr_job_head_id) {

        MapJobProblemModel mapJobProblemModel = null;

        try {

            Connection connection = DataBaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(PROBLEM_QUERY);
            preparedStatement.setInt(1, tr_job_head_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                mapJobProblemModel = new MapJobProblemModel(
                        resultSet.getInt("id"),
                        resultSet.getInt("tr_job_head_id"),
                        resultSet.getInt("mst_problem_id"),
                        resultSet.getString("remark"));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return mapJobProblemModel;
    }
}