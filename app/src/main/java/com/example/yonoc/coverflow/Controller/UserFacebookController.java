package com.example.yonoc.coverflow.Controller;

import com.example.yonoc.coverflow.Model.DAO.DAOFacebookUsers;
import com.example.yonoc.coverflow.Model.POJO.UserFacebook;
import com.example.yonoc.coverflow.Utils.ResultsListener;

public class UserFacebookController {

    //Obtener los usuarios de facebook llamando al DAO

    public void obtenerUserFacebookData(final ResultsListener<UserFacebook> resultsListenerDeVistaDeFacebookUser, String actualToken){

        ResultsListener<UserFacebook> listenerDeFacebookUser = new ResultsListener<UserFacebook>() {
            @Override
            public void finish(UserFacebook resultado) {
                if (resultado != null) {
                    resultsListenerDeVistaDeFacebookUser.finish(resultado);
                }
            }
        };

        UserFacebook userFacebook = null;
        if (hayInternet()){
            DAOFacebookUsers daoFacebookUsers = new DAOFacebookUsers();
            daoFacebookUsers.getUserFacebook(listenerDeFacebookUser, actualToken);
        } else {
            resultsListenerDeVistaDeFacebookUser.finish(userFacebook);
        }
    }

    public Boolean hayInternet(){
        return true;
    }
}
