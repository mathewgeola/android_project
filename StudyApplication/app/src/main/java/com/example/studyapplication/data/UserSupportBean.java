package com.example.studyapplication.data;

import com.google.gson.annotations.SerializedName;


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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
