package com.example.yonoc.coverflow.Utils;

import com.example.yonoc.coverflow.Model.POJO.UserFacebook;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetUserFacebook {

    @GET("me?fields=email,name,first_name,last_name,picture,friends")
    Call<UserFacebook> getUserFacebook(@Query("access_token") String actualToken);
}
