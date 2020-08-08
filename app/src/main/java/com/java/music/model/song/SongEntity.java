package com.java.music.model.song;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SongEntity implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("songname")
    @Expose
    private String songname;
    @SerializedName("authorid")
    @Expose
    private Integer authorid;
    @SerializedName("createdate")
    @Expose
    private String createdate;
    @SerializedName("modifieduser")
    @Expose
    private String modifieduser;
    @SerializedName("modifieddate")
    @Expose
    private String modifieddate;
    @SerializedName("uploadsource")
    @Expose
    private String uploadsource;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("range")
    @Expose
    private Integer range;
    @SerializedName("active")
    @Expose
    private Boolean active;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSongname() {
        return songname;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }

    public Integer getAuthorid() {
        return authorid;
    }

    public void setAuthorid(Integer authorid) {
        this.authorid = authorid;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getModifieduser() {
        return modifieduser;
    }

    public void setModifieduser(String modifieduser) {
        this.modifieduser = modifieduser;
    }

    public String getModifieddate() {
        return modifieddate;
    }

    public void setModifieddate(String modifieddate) {
        this.modifieddate = modifieddate;
    }

    public String getUploadsource() {
        return uploadsource;
    }

    public void setUploadsource(String uploadsource) {
        this.uploadsource = uploadsource;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getRange() {
        return range;
    }

    public void setRange(Integer range) {
        this.range = range;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
