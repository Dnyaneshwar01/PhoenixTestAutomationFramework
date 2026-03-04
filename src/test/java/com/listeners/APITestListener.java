package com.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Arrays;

public class APITestListener implements ITestListener {

    private static final Logger LOGGER = LogManager.getLogger(APITestListener.class);

    public void onTestStart(ITestResult result) {
        LOGGER.info("Started the test {} ", result.getName());
        LOGGER.info("Test Class {} ", result.getMethod().getTestClass());
        LOGGER.info("Description {} ", result.getMethod().getDescription());
        LOGGER.info("Group {} ", Arrays.toString(result.getMethod().getGroups()));
    }

    public void onTestSuccess(ITestResult result) {
        long startTime = result.getStartMillis();
        long entTime = result.getEndMillis();
        LOGGER.info("Total Duration: {} sec ", (entTime - startTime) / 60);
        LOGGER.info("{} - Test Passed!!!", result.getName());
    }

    public void onTestFailure(ITestResult result) {
        LOGGER.error("{} - Test FAILED!!!", result.getName());
        LOGGER.error("Error Message", result.getThrowable().getMessage());
        LOGGER.error(result.getThrowable());
    }

    public void onTestSkipped(ITestResult result) {
        LOGGER.info("{} - Test SKIPPED!!!", result.getName());
        LOGGER.error(result.getThrowable());
    }

    public void onStart(ITestContext context){
        LOGGER.info("***************** Starting the Phoenix Framework *****************");
    }

    public void onFinish(ITestContext context){
        LOGGER.info("***************** Finish  *****************");
    }


}
