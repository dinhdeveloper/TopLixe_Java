package com.java.music.model;

import java.io.Serializable;

public class UserEntityModel implements Serializable {


    private String apiToken;
    private UserEntityBean userEntity;

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public UserEntityBean getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntityBean userEntity) {
        this.userEntity = userEntity;
    }
}
