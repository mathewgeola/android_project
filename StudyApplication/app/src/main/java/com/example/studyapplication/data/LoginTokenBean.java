package com.example.studyapplication.data;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginTokenBean {
    /*
    {"token":"QpwL5tke4Pnpja7X4"}
    */

    @SerializedName("token")
    String token;
}
