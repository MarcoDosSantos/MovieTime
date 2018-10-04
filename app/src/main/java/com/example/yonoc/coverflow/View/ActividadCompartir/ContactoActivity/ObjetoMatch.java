package com.example.yonoc.coverflow.View.ActividadCompartir.ContactoActivity;

/**
 * Created by yonoc on 7/8/2018.
 */

public class ObjetoMatch {

    private String nombre, userId;


    public ObjetoMatch(String nombre, String userId) {
        this.nombre = nombre;
        this.userId = userId;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUserId() {
        return userId;
    }
}
