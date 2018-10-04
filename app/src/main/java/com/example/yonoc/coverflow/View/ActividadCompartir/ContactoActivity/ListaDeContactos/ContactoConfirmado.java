package com.example.yonoc.coverflow.View.ActividadCompartir.ContactoActivity.ListaDeContactos;

/**
 * Created by yonoc on 7/a14/2018.
 */

class ContactoConfirmado {

    String userId, otroUserId, urlImagen, nombre, pelicula, cine, chatId;

    public ContactoConfirmado(String userId, String otroUserId, String urlImagen, String nombre, String pelicula, String cine, String chatId) {
        this.userId = userId;
        this.otroUserId = otroUserId;
        this.urlImagen = urlImagen;
        this.nombre = nombre;
        this.pelicula = pelicula;
        this.cine = cine;
        this.chatId = chatId;
    }



    public String getUserId() {
        return userId;
    }

    public String getOtroUserId() {
        return otroUserId;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPelicula() {
        return pelicula;
    }

    public String getCine() {
        return cine;
    }

    public String getChatId() {
        return chatId;
    }
}
