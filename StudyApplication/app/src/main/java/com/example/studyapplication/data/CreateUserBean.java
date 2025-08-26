package com.example.studyapplication.data;

import com.google.gson.annotations.SerializedName;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
