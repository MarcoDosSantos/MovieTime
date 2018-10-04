package com.example.yonoc.coverflow.Model.POJO.ContenedoresRetrofit;

import com.example.yonoc.coverflow.Model.POJO.Trailer;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContenedorDeTrailersRetrofit {
    @SerializedName("results")
    public List<Trailer> listaDeTrailers;

    public ContenedorDeTrailersRetrofit(List<Trailer> listaDeTrailers){
        this.listaDeTrailers = listaDeTrailers;
    }
    public List<Trailer> getListaDeTrailers() {
        return listaDeTrailers;
    }

    public void setListaDeTrailers(List<Trailer> listaDeTrailers) {
        this.listaDeTrailers = listaDeTrailers;
    }
}
