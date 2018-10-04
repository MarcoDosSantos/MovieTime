package com.example.yonoc.coverflow.Model.DAO;
import com.example.yonoc.coverflow.Model.POJO.UserFacebook;
import com.example.yonoc.coverflow.Utils.GetUserFacebook;
import com.example.yonoc.coverflow.Utils.ResultsListener;
import com.example.yonoc.coverflow.View.LoginRegisterActivities.LoginActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DAOFacebookUsers {

    private String baseURL;
    private Retrofit retrofit;
    private GetUserFacebook servicioFacebook;

    public DAOFacebookUsers() {
        baseURL = "https://graph.facebook.com/v2.5/";
        retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        servicioFacebook = retrofit.create(GetUserFacebook.class);
    }

    // Obtener los usuarios de facebook llamando a la interface de facebook

    public void getUserFacebook(final ResultsListener<UserFacebook> escuchadorDelFacebookUser, String actualToken){

        String actualUserToken = actualToken;

        Call<UserFacebook> retrofitUserFacebook = servicioFacebook.getUserFacebook(actualUserToken);

        retrofitUserFacebook.enqueue(new Callback<UserFacebook>() {
            @Override
            public void onResponse(Call<UserFacebook> call, Response<UserFacebook> response) {
                UserFacebook userFacebook = response.body();
                escuchadorDelFacebookUser.finish(userFacebook);
            }

            @Override
            public void onFailure(Call<UserFacebook> call, Throwable t) {
                escuchadorDelFacebookUser.finish(null);
            }
        });
    }
}
