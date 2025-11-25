package com.api.constant;

public enum Warrantly_Status {
    IN_WARRANTY(1), OUT_WARRANTY(2);

    int code;

    Warrantly_Status(int code){
        this.code = code;
    }

    public int getCode(){
        return code;
    }
}
