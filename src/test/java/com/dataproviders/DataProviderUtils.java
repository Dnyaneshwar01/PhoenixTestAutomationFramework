package com.dataproviders;

import com.api.records.model.CreateJobPayload;
import com.api.utils.CSVReaderUtility;
import com.api.utils.CreateJobBeanMapper;
import com.dataproviders.api.bean.CreateJobBean;
import com.dataproviders.api.bean.UserBean;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataProviderUtils {

    @DataProvider(name = "LoginAPIDataProvider", parallel = true)
    public static Iterator<UserBean> loginAPIDataProvider(){
        return CSVReaderUtility.loadCSV("testData/LoginCreds.csv",UserBean.class);
    }

    @DataProvider(name = "CreateJobAPIDataProvider", parallel = true)
    public static Iterator<CreateJobPayload> createJobDataProvider() {
        Iterator<CreateJobBean> createJobBeanIterator = CSVReaderUtility.loadCSV("testData/CreateJobData.csv",
                CreateJobBean.class);

        List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();

        CreateJobBean tempBean;
        CreateJobPayload tempPayload;

        while (createJobBeanIterator.hasNext()){
            tempBean = createJobBeanIterator.next();
            tempPayload = CreateJobBeanMapper.mapper(tempBean);
            payloadList.add(tempPayload);
        }

        return payloadList.iterator();
    }




}
