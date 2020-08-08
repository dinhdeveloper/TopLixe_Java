package com.java.music.model.actor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CategorySongEntityList implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("categoryname")
    @Expose
    private String categoryname;
    @SerializedName("range")
    @Expose
    private Integer range;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public Integer getRange() {
        return range;
    }

    public void setRange(Integer range) {
        this.range = range;
    }
}
