package com.example.studyapplication;

import androidx.annotation.DrawableRes;

public class ContactPerson {
    private final String name;
    private final int avatar;

    ContactPerson(String name, @DrawableRes int avatar) {
        this.name = name;
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public int getAvatar() {
        return avatar;
    }
}
