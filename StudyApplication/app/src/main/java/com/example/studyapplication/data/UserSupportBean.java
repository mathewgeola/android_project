package com.example.studyapplication.data;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSupportBean {
    /*
    {
        "url": "https://contentcaddy.io?utm_source=reqres&utm_medium=json&utm_campaign=referral",
        "text": "Tired of writing endless social media content? Let Content Caddy generate it for you."
    }
    */

    @SerializedName("url")
    String url;

    @SerializedName("text")
    String text;
}
