package com.java.music.model.song;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AlbumEntityModel implements Serializable {
    @SerializedName("albumEntity")
    @Expose
    private AlbumEntity albumEntity;
    @SerializedName("songEntity")
    @Expose
    private List<SongEntity> songDTOList = null;
    @SerializedName("singerEntity")
    @Expose
    private List<Object> singerEntity = null;

    public AlbumEntity getAlbumEntity() {
        return albumEntity;
    }

    public void setAlbumEntity(AlbumEntity albumEntity) {
        this.albumEntity = albumEntity;
    }

    public List<SongEntity> getSongEntity() {
        return songDTOList;
    }

    public void setSongEntity(List<SongEntity> songEntity) {
        this.songDTOList = songEntity;
    }

    public List<Object> getSingerEntity() {
        return singerEntity;
    }

    public void setSingerEntity(List<Object> singerEntity) {
        this.singerEntity = singerEntity;
    }
}
