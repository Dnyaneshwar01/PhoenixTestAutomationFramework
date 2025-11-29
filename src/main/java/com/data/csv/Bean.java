package com.data.csv;

import com.opencsv.bean.CsvBindByName;

public class Bean {
    @CsvBindByName(column = "username")
    private String userName;
    @CsvBindByName(column = "password")    private String password;

    public Bean(){

    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
