package com.greenshopap.bel.greenshop_eca;

public class StoreUserInfo {
    public String user_name,user_mobile,user_email,user_password;

    public StoreUserInfo()
    {

    }

    public StoreUserInfo(String user_name, String user_mobile, String user_email, String user_password) {
        this.user_name = user_name;
        this.user_mobile = user_mobile;
        this.user_email = user_email;
        this.user_password = user_password;
    }
}
