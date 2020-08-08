package com.java.music.model.singer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.java.music.model.song.SongEntityModel;

import java.io.Serializable;
import java.util.List;

public class SingerEntityModel implements Serializable {
    @SerializedName("singerEntity")
    @Expose
    private SingerEntity singerEntity;
    @SerializedName("songDTOList")
    @Expose
    private List<SongEntityModel> songDTOList = null;

    public SingerEntity getSingerEntity() {
        return singerEntity;
    }

    public void setSingerEntity(SingerEntity singerEntity) {
        this.singerEntity = singerEntity;
    }

    public List<SongEntityModel> getSongDTOList() {
        return songDTOList;
    }

    public void setSongDTOList(List<SongEntityModel> songDTOList) {
        this.songDTOList = songDTOList;
    }
}
