package com.example.yonoc.coverflow.Model.POJO;

public class FacebookPicture {

    public FacebookData data;

    public FacebookPicture (FacebookData aData){
        this.data = aData;
    }

    public FacebookData getData() {
        return data;
    }
}
