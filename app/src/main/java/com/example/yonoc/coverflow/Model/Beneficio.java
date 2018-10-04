package com.example.yonoc.coverflow.Model;

public class Beneficio {

    private Integer imagenCompartidor;
    private String nombreCompartidor;
    private String detalleCortoBeneficio;
    private Boolean isChecked;

    public Beneficio (Integer imagenCompartidor, String nombreCompartidor, String detalleCortoBeneficio, Boolean isChecked){
        this.imagenCompartidor = imagenCompartidor;
        this.nombreCompartidor = nombreCompartidor;
        this.detalleCortoBeneficio = detalleCortoBeneficio;
        this.isChecked = isChecked;
    }

    public Integer getImagenCompartidor() {
        return imagenCompartidor;
    }

    public String getNombreCompartidor() {
        return nombreCompartidor;
    }

    public String getDetalleCortoBeneficio() {
        return detalleCortoBeneficio;
    }

    public Boolean getChecked() {
        return isChecked;
    }
}

