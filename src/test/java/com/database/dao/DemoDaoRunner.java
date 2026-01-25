package com.database.dao;

import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerProductDBModel;

public class DemoDaoRunner {

    public static void main(String[] args)  {
        //CustomerDBModel customerDBModel = CustomerDao.getCustomerInfo(165206);
//        System.out.println(customerDBModel);

//        CustomerAddressDBModel customerAddressDBModel = CustomerAddressDao.getCustomerAddressData(165396);
//        System.out.println(customerAddressDBModel);

        CustomerProductDBModel customerProductDBModel = CustomerProductDao.getCustomerProductData(165386);
        System.out.println(customerProductDBModel);
    }
}
