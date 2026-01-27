package com.database.model;

import com.database.dao.JobHeadDao;

public class DemoRunner {

    public static void main(String[] args) {
        JobHeadModel jobHeadModel=JobHeadDao.getDataFromJobHead(168597);
        System.out.println(jobHeadModel);
    }
}