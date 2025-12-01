package com.dataProvider.api.bean;

import com.opencsv.bean.CsvBindByName;

public class UserBean {
    @CsvBindByName(column = "username")
    private String userName;
    @CsvBindByName(column = "password")
    private String password;

    public UserBean(){

    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
