package com.androidbase.entity;

import java.io.Serializable;

/**
 * Created by qianjin on 2015/5/11.
 */
public class Tag implements Serializable{

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
