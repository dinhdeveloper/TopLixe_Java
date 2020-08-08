package com.java.music.model.actor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.java.music.model.film.FilmDTOList;
import com.java.music.model.film.FilmEntityModel;

import java.io.Serializable;
import java.util.List;

public class ActorEntityModel implements Serializable {
    @SerializedName("actorEntity")
    @Expose
    private ActorEntity actorEntity;
    @SerializedName("imageEntity")
    @Expose
    private ImageEntity imageEntity;
    @SerializedName("filmDTOList")
    @Expose
    private List<FilmEntityModel> filmDTOList = null;

    public ActorEntity getActorEntity() {
        return actorEntity;
    }

    public void setActorEntity(ActorEntity actorEntity) {
        this.actorEntity = actorEntity;
    }

    public ImageEntity getImageEntity() {
        return imageEntity;
    }

    public void setImageEntity(ImageEntity imageEntity) {
        this.imageEntity = imageEntity;
    }

    public List<FilmEntityModel> getFilmDTOList() {
        return filmDTOList;
    }

    public void setFilmDTOList(List<FilmEntityModel> filmDTOList) {
        this.filmDTOList = filmDTOList;
    }

}
