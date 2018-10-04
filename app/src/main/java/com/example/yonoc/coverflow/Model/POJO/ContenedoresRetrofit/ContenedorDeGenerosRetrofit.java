package com.example.yonoc.coverflow.Model.POJO.ContenedoresRetrofit;

import com.example.yonoc.coverflow.Model.POJO.Genero;

import java.util.List;

/**
 * Created by yonoc on 6/9/2018.
 */

public class ContenedorDeGenerosRetrofit {

    private List<Genero> genres;

    public ContenedorDeGenerosRetrofit(List<Genero> genres) {

        this.genres = genres;
    }

    public List<Genero> getGenres() {
        return genres;
    }
}
