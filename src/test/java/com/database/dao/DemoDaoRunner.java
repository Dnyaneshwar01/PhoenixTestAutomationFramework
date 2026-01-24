package com.database.dao;

import com.database.model.CustomerAddressDBModel;

public class DemoDaoRunner {

    public static void main(String[] args)  {
        //CustomerDBModel customerDBModel = CustomerDao.getCustomerInfo(165206);
//        System.out.println(customerDBModel);

        CustomerAddressDBModel customerAddressDBModel = CustomerAddressDao.getCustomerAddressData(165396);
        System.out.println(customerAddressDBModel);
    }
}
