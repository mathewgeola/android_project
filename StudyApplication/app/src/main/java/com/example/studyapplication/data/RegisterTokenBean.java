package com.example.studyapplication.data;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterTokenBean {
    /*
    {"id":4,"token":"QpwL5tke4Pnpja7X4"}
    */

    @SerializedName("id")
    private Integer id;

    @SerializedName("token")
    String token;


}
