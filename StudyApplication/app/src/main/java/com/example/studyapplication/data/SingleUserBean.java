package com.example.studyapplication.data;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleUserBean {
    @SerializedName("data")
    UserBean data;
}
