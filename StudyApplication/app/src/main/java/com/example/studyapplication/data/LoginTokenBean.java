package com.example.studyapplication.data;

import com.google.gson.annotations.SerializedName;


public class LoginTokenBean {
    /*
    {"token":"QpwL5tke4Pnpja7X4"}
    */

    @SerializedName("token")
    String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
