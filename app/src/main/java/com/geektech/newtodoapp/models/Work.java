package com.geektech.newtodoapp.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Work implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private String title;
    private String desc;

    public Work(){

    }

    public Work(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
