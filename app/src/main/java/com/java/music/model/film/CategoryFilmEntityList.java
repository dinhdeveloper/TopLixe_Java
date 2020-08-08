package com.java.music.model.film;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CategoryFilmEntityList implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("categoryfilm")
    @Expose
    private String categoryfilm;
    @SerializedName("range")
    @Expose
    private Integer range;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryfilm() {
        return categoryfilm;
    }

    public void setCategoryfilm(String categoryfilm) {
        this.categoryfilm = categoryfilm;
    }

    public Integer getRange() {
        return range;
    }

    public void setRange(Integer range) {
        this.range = range;
    }
}
