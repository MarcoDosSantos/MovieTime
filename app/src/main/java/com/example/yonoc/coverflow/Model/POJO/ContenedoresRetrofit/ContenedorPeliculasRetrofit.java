package com.example.yonoc.coverflow.Model.POJO.ContenedoresRetrofit;

import com.example.yonoc.coverflow.Model.POJO.Pelicula;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yonoc on 6/9/2018.
 */

public class ContenedorPeliculasRetrofit {

    @SerializedName("results")
    private List<Pelicula> listaDePeliculas;
    private Integer total_pages;

    public ContenedorPeliculasRetrofit(List<Pelicula> listaDePeliculas, Integer total_pages) {
        this.listaDePeliculas = listaDePeliculas;
        this.total_pages = total_pages;
    }

    public List<Pelicula> getListaDePeliculas() {
        return listaDePeliculas;
    }

    public Integer getTotal_pages() {
        return total_pages;
    }
}
