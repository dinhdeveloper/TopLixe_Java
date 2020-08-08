package com.java.music.model.film;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FilmEntity implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("filmname")
    @Expose
    private String filmname;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("directorid")
    @Expose
    private Integer directorid;
    @SerializedName("yearreleased")
    @Expose
    private Integer yearreleased;
    @SerializedName("uploadsource")
    @Expose
    private String uploadsource;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("createdate")
    @Expose
    private String createdate;
    @SerializedName("length")
    @Expose
    private Integer length;
    @SerializedName("info")
    @Expose
    private String info;
    @SerializedName("range")
    @Expose
    private Integer range;
    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("actorid")
    @Expose
    private Integer actorid;
    @SerializedName("index")
    @Expose
    private Integer index;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilmname() {
        return filmname;
    }

    public void setFilmname(String filmname) {
        this.filmname = filmname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getDirectorid() {
        return directorid;
    }

    public void setDirectorid(Integer directorid) {
        this.directorid = directorid;
    }

    public Integer getYearreleased() {
        return yearreleased;
    }

    public void setYearreleased(Integer yearreleased) {
        this.yearreleased = yearreleased;
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

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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

    public Integer getActorid() {
        return actorid;
    }

    public void setActorid(Integer actorid) {
        this.actorid = actorid;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
