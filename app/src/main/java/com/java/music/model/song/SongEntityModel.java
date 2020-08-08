package com.java.music.model.song;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.java.music.model.actor.CategorySongEntityList;
import com.java.music.model.actor.SingerEntityList;

import java.io.Serializable;
import java.util.List;

public class SongEntityModel implements Serializable {

    @SerializedName("songEntity")
    @Expose
    private SongEntity songEntity;
    @SerializedName("albumEntity")
    @Expose
    private Object albumEntity;
    @SerializedName("authorEntity")
    @Expose
    private AuthorEntity authorEntity;
    @SerializedName("imageEntity")
    @Expose
    private Object  imageEntity;
    @SerializedName("singerEntityList")
    @Expose
    private List<SingerEntityList> singerEntityList = null;
    @SerializedName("uploadEntityList")
    @Expose
    private List<Object> uploadEntityList = null;
    @SerializedName("categorySongEntityList")
    @Expose
    private List<CategorySongEntityList> categorySongEntityList = null;

    public SongEntity getSongEntity() {
        return songEntity;
    }

    public void setSongEntity(SongEntity songEntity) {
        this.songEntity = songEntity;
    }

    public Object getAlbumEntity() {
        return albumEntity;
    }

    public void setAlbumEntity(Object albumEntity) {
        this.albumEntity = albumEntity;
    }

    public AuthorEntity getAuthorEntity() {
        return authorEntity;
    }

    public void setAuthorEntity(AuthorEntity authorEntity) {
        this.authorEntity = authorEntity;
    }

    public Object getImageEntity() {
        return imageEntity;
    }

    public void setImageEntity(Object imageEntity) {
        this.imageEntity = imageEntity;
    }

    public List<SingerEntityList> getSingerEntityList() {
        return singerEntityList;
    }

    public void setSingerEntityList(List<SingerEntityList> singerEntityList) {
        this.singerEntityList = singerEntityList;
    }

    public List<Object> getUploadEntityList() {
        return uploadEntityList;
    }

    public void setUploadEntityList(List<Object> uploadEntityList) {
        this.uploadEntityList = uploadEntityList;
    }

    public List<CategorySongEntityList> getCategorySongEntityList() {
        return categorySongEntityList;
    }

    public void setCategorySongEntityList(List<CategorySongEntityList> categorySongEntityList) {
        this.categorySongEntityList = categorySongEntityList;
    }
}
