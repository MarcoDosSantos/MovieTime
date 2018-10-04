package com.example.yonoc.coverflow.Model.POJO;

import com.google.firebase.database.Transaction;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Trailer {




    public String key;

    public Trailer(String key){
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        key = key;
    }
}
