package com.java.music.model;

import java.io.Serializable;
import java.util.List;

public class CustomerModel implements Serializable {

    private UserEntityBean userEntity;
    private RoleEntityBean roleEntity;
    private List<ImageEntity> imageEntity;

    public UserEntityBean getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntityBean userEntity) {
        this.userEntity = userEntity;
    }

    public RoleEntityBean getRoleEntity() {
        return roleEntity;
    }

    public void setRoleEntity(RoleEntityBean roleEntity) {
        this.roleEntity = roleEntity;
    }

    public List<?> getImageEntity() {
        return imageEntity;
    }

    public void setImageEntity(List<ImageEntity> imageEntity) {
        this.imageEntity = imageEntity;
    }

}
