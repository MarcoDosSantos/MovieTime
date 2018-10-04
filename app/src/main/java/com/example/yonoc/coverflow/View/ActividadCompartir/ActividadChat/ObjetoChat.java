package com.example.yonoc.coverflow.View.ActividadCompartir.ActividadChat;

/**
 * Created by yonoc on 7/9/2018.
 */

public class ObjetoChat {

    private String mensaje, userId, otroUserId, nombre;

    public ObjetoChat(String mensaje, String userId, String otroUserId, String nombre) {
        this.mensaje = mensaje;
        this.userId = userId;
        this.otroUserId = otroUserId;
        this.nombre = nombre;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getUserId() {
        return userId;
    }

    public String getOtroUserId() {
        return otroUserId;
    }

    public String getNombre() {
        return nombre;
    }
}
