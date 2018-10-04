package com.example.yonoc.coverflow.Model.POJO;


public class UserFacebook {

    public String name;
    public FacebookPicture picture;

    public UserFacebook(String aName, FacebookPicture aPicture){
        this.name = aName;
        this.picture = aPicture;
    }

    public String getName() {
        return name;
    }

    public FacebookPicture getPicture() {
        return picture;
    }
}
