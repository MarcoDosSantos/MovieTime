package com.example.yonoc.coverflow.Model.POJO;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by yonoc on 6/a27/2018.
 */

@Entity
public class Genero {

    @PrimaryKey @NonNull
    private Integer id;
    private String name;

    public Genero(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
