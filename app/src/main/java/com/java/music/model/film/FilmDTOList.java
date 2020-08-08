package com.java.music.model.film;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.java.music.model.actor.ActorEntityList;

import java.io.Serializable;
import java.util.List;

public class FilmDTOList implements Serializable {
    @SerializedName("filmEntity")
    @Expose
    private FilmEntity filmEntity;
    @SerializedName("directorEntity")
    @Expose
    private Object directorEntity;
    @SerializedName("actorEntityList")
    @Expose
    private List<ActorEntityList> actorEntityList = null;
    @SerializedName("imageEntity")
    @Expose
    private List<Object> imageEntity = null;
    @SerializedName("uploadEntityList")
    @Expose
    private List<Object> uploadEntityList = null;
    @SerializedName("categoryFilmEntityList")
    @Expose
    private List<CategoryFilmEntityList> categoryFilmEntityList = null;
    @SerializedName("seriCategoryFilmEntity")
    @Expose
    private Object seriCategoryFilmEntity;

    public FilmEntity getFilmEntity() {
        return filmEntity;
    }

    public void setFilmEntity(FilmEntity filmEntity) {
        this.filmEntity = filmEntity;
    }

    public Object getDirectorEntity() {
        return directorEntity;
    }

    public void setDirectorEntity(Object directorEntity) {
        this.directorEntity = directorEntity;
    }

    public List<ActorEntityList> getActorEntityList() {
        return actorEntityList;
    }

    public void setActorEntityList(List<ActorEntityList> actorEntityList) {
        this.actorEntityList = actorEntityList;
    }

    public List<Object> getImageEntity() {
        return imageEntity;
    }

    public void setImageEntity(List<Object> imageEntity) {
        this.imageEntity = imageEntity;
    }

    public List<Object> getUploadEntityList() {
        return uploadEntityList;
    }

    public void setUploadEntityList(List<Object> uploadEntityList) {
        this.uploadEntityList = uploadEntityList;
    }

    public List<CategoryFilmEntityList> getCategoryFilmEntityList() {
        return categoryFilmEntityList;
    }

    public void setCategoryFilmEntityList(List<CategoryFilmEntityList> categoryFilmEntityList) {
        this.categoryFilmEntityList = categoryFilmEntityList;
    }

    public Object getSeriCategoryFilmEntity() {
        return seriCategoryFilmEntity;
    }

    public void setSeriCategoryFilmEntity(Object seriCategoryFilmEntity) {
        this.seriCategoryFilmEntity = seriCategoryFilmEntity;
    }
}
