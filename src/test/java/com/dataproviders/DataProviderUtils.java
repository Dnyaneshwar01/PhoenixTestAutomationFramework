package com.dataproviders;

import com.api.records.model.CreateJobPayload;
import com.api.records.model.UserCredentials;
import com.api.utils.*;
import com.dataproviders.api.bean.CreateJobBean;
import com.dataproviders.api.bean.UserBean;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Dnyaneshwar Ubale
 */

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

    @DataProvider(name = "CreateJobAPIFakerDataProvider", parallel = true)
    public static Iterator<CreateJobPayload> createJobFakeDataProvider() {
        String fakerCount = System.getProperty("fakerCount", "5");
        int fakerCountInt = Integer.parseInt(fakerCount);
        Iterator<CreateJobPayload> payloadIterator = FakerDataGenerator.generateFakeCreateJobData(fakerCountInt);
        return payloadIterator;
    }

    @DataProvider(name = "LoginAPIJSONDataProvider", parallel = true)
    public static Iterator<UserCredentials> loginAPIJsonDataProvider(){
        return JsonReaderUtil.loadJSON("testData/loginAPITestData.json",UserCredentials[].class);
    }

    @DataProvider(name = "CreateJobAPIJSONDataProvider", parallel = true)
    public static Iterator<CreateJobPayload> createJobAPIJsonDataProvider(){
        return JsonReaderUtil.loadJSON("testData/CreateJobAPIData.json",CreateJobPayload[].class);
    }

    @DataProvider(name = "LoginAPIExcelDataProvider", parallel = true)
    public static Iterator<UserBean> loginAPIExcelDataProvider(){
        return ExcelReaderUtil2.loadTestData("LoginTestData", UserBean.class);
    }

    @DataProvider(name = "CreateJobAPIExcelDataProvider", parallel = true)
    public static Iterator<CreateJobPayload> CreateJobAPIExcelDataProvider(){
        Iterator<CreateJobBean> iterator = ExcelReaderUtil2.loadTestData("CreateJobTestData", CreateJobBean.class);

        List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();

        CreateJobBean tempBean;
        CreateJobPayload tempPayload;

        while (iterator.hasNext()){
            tempBean = iterator.next();
            tempPayload = CreateJobBeanMapper.mapper(tempBean);
            payloadList.add(tempPayload);
        }

        return payloadList.iterator();
    }

}
