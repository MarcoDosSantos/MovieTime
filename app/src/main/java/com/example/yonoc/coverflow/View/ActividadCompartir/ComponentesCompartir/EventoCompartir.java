package com.example.yonoc.coverflow.View.ActividadCompartir.ComponentesCompartir;

public class EventoCompartir {
    String nombrePelicula;
    String nombreUsuario;
    String otroUserID;
    String urlImagenPerfil;
    String keyTicket;
    String nombreCine;
    String poster;
    String horario;
    String fecha;

    public EventoCompartir(String nombrePelicula, String nombreUsuario, String otroUserID, String urlImagenPerfil, String keyTicket, String nombreCine, String poster, String horario, String fecha) {
        this.nombrePelicula = nombrePelicula;
        this.nombreUsuario = nombreUsuario;
        this.otroUserID = otroUserID;
        this.urlImagenPerfil = urlImagenPerfil;
        this.keyTicket = keyTicket;
        this.nombreCine = nombreCine;
        this.poster = poster;
        this.horario = horario;
        this.fecha = fecha;
    }

    public String getNombrePelicula() {
        return nombrePelicula;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getOtroUserID() {
        return otroUserID;
    }

    public String getUrlImagenPerfil() {
        return urlImagenPerfil;
    }

    public String getKeyTicket() {
        return keyTicket;
    }

    public String getNombreCine() {
        return nombreCine;
    }

    public String getPoster() {
        return poster;
    }

    public String getHorario() {
        return horario;
    }

    public String getFecha() {
        return fecha;
    }
}
