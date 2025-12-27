package com.dataproviders.api.bean;

import com.opencsv.bean.CsvBindByName;

public class UserBean {
    @CsvBindByName(column = "username")
    private String username;
    @CsvBindByName(column = "password")
    private String password;

    public UserBean(){
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "userName='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }




}
