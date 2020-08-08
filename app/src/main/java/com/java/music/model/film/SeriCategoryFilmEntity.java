package com.java.music.model.film;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SeriCategoryFilmEntity implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("ext")
    @Expose
    private String ext;
    @SerializedName("range")
    @Expose
    private Integer range;
    @SerializedName("index")
    @Expose
    private Integer index;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public Integer getRange() {
        return range;
    }

    public void setRange(Integer range) {
        this.range = range;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

}
