package com.example.studyapplication.data;

import com.google.gson.annotations.SerializedName;

public class RegisterTokenBean {
    /*
    {"id":4,"token":"QpwL5tke4Pnpja7X4"}
    */

    @SerializedName("id")
    private Integer id;

    @SerializedName("token")
    String token;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
