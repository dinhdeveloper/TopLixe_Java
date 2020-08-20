package com.java.music.model;

import java.io.Serializable;

public class UserEntityBean implements Serializable {
    private int id;
    private String username;
    private String password;
    private String email;
    private String ext;
    private String follow;
    private int roleid;
    private String img;
    private String displayname;
    private boolean active;
    private String createDate;
    private String createUser;
    private String updateDate;
    private String updateUser;
    private String userWebToken;
    private String webTokenCreateDate;
    private String userMbToken;
    private String mbTokenCreateDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getFollow() {
        return follow;
    }

    public void setFollow(String follow) {
        this.follow = follow;
    }

    public int getRoleid() {
        return roleid;
    }

    public void setRoleid(int roleid) {
        this.roleid = roleid;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getUserWebToken() {
        return userWebToken;
    }

    public void setUserWebToken(String userWebToken) {
        this.userWebToken = userWebToken;
    }

    public String getWebTokenCreateDate() {
        return webTokenCreateDate;
    }

    public void setWebTokenCreateDate(String webTokenCreateDate) {
        this.webTokenCreateDate = webTokenCreateDate;
    }

    public String getUserMbToken() {
        return userMbToken;
    }

    public void setUserMbToken(String userMbToken) {
        this.userMbToken = userMbToken;
    }

    public String getMbTokenCreateDate() {
        return mbTokenCreateDate;
    }

    public void setMbTokenCreateDate(String mbTokenCreateDate) {
        this.mbTokenCreateDate = mbTokenCreateDate;
    }
}
