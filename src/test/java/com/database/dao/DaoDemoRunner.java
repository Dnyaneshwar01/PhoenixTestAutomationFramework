package com.database.dao;

import com.api.records.model.CreateJobPayload;
import com.api.utils.CreateJobBeanMapper;
import com.dataproviders.api.bean.CreateJobBean;

import java.util.ArrayList;
import java.util.List;

public class DaoDemoRunner {

    public static void main(String[] args) {
        List<CreateJobBean> beanList = CreateJobPayloadDataDao.getCreateJobPayloadData();
        List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();
        for (CreateJobBean createJobBean : beanList) {
            CreateJobPayload payload = CreateJobBeanMapper.mapper(createJobBean);
            payloadList.add(payload);
        }

        System.out.println("--------------------------------------------------------------");
        for (CreateJobPayload payload : payloadList) {
            System.out.println(payload);
        }
    }
}
