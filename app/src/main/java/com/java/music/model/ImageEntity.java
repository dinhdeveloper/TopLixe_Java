package com.java.music.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ImageEntity implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("path")
    @Expose
    private Object path;
    @SerializedName("size")
    @Expose
    private Object size;
    @SerializedName("fileextension")
    @Expose
    private Object fileextension;
    @SerializedName("model")
    @Expose
    private Object model;
    @SerializedName("entryid")
    @Expose
    private Integer entryid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getPath() {
        return path;
    }

    public void setPath(Object path) {
        this.path = path;
    }

    public Object getSize() {
        return size;
    }

    public void setSize(Object size) {
        this.size = size;
    }

    public Object getFileextension() {
        return fileextension;
    }

    public void setFileextension(Object fileextension) {
        this.fileextension = fileextension;
    }

    public Object getModel() {
        return model;
    }

    public void setModel(Object model) {
        this.model = model;
    }

    public Integer getEntryid() {
        return entryid;
    }

    public void setEntryid(Integer entryid) {
        this.entryid = entryid;
    }
}
