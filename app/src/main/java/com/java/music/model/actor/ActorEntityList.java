package com.java.music.model.actor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ActorEntityList implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("actorname")
    @Expose
    private String actorname;
    @SerializedName("ext")
    @Expose
    private String ext;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActorname() {
        return actorname;
    }

    public void setActorname(String actorname) {
        this.actorname = actorname;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }
}
