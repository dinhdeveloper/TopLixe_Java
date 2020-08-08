package com.java.music.model.song;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AlbumEntity implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("albumname")
    @Expose
    private String albumname;
    @SerializedName("datereleased")
    @Expose
    private String datereleased;
    @SerializedName("range")
    @Expose
    private Integer range;
    @SerializedName("singerid")
    @Expose
    private Integer singerid;
    @SerializedName("index")
    @Expose
    private Integer index;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAlbumname() {
        return albumname;
    }

    public void setAlbumname(String albumname) {
        this.albumname = albumname;
    }

    public String  getDatereleased() {
        return datereleased;
    }

    public void setDatereleased(String datereleased) {
        this.datereleased = datereleased;
    }

    public Integer getRange() {
        return range;
    }

    public void setRange(Integer range) {
        this.range = range;
    }

    public Integer getSingerid() {
        return singerid;
    }

    public void setSingerid(Integer singerid) {
        this.singerid = singerid;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
