package com.example.studyapplication.data;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CreateUserBean {
    /*
    {
        "name": "morpheus",
        "job": "leader",
        "id": "276",
        "createdAt": "2025-08-17T10:25:37.960Z"
    }
    */

    @SerializedName("name")
    String name;


    @SerializedName("job")
    String job;


    @SerializedName("id")
    String id;


    @SerializedName("createdAt")
    String createdAt;
}
