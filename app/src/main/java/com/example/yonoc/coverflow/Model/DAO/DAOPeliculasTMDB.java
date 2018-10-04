package com.example.yonoc.coverflow.Model.DAO;

import com.example.yonoc.coverflow.Model.POJO.ContenedoresRetrofit.ContenedorDeGenerosRetrofit;
import com.example.yonoc.coverflow.Model.POJO.ContenedoresRetrofit.ContenedorDeTrailersRetrofit;
import com.example.yonoc.coverflow.Model.POJO.Trailer;
import com.example.yonoc.coverflow.Model.POJO.ContenedoresRetrofit.ContenedorPeliculasRetrofit;
import com.example.yonoc.coverflow.Model.POJO.Genero;
import com.example.yonoc.coverflow.Model.POJO.Pelicula;
import com.example.yonoc.coverflow.Utils.ResultsListener;
import com.example.yonoc.coverflow.Model.DAO.RetrofitServices.ServicioPelicula;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yonoc on 6/9/2018.
 */

public class DAOPeliculasTMDB {

    private String baseURL;
    private Retrofit retrofit;
    private ServicioPelicula servicioPelicula;

    public DAOPeliculasTMDB() {
        baseURL = "https://api.themoviedb.org/";
        retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        servicioPelicula = retrofit.create(ServicioPelicula.class);
    }

    public void getNowPlaying(final ResultsListener<List<Pelicula>> escuchadorDelControlador/*, final ResultsListener<Integer> escuchadorDeNumeroDePaginas*/){

        Integer paginaALLamar = 1;


        Call<ContenedorPeliculasRetrofit> retrofitListener = servicioPelicula.getNowPlaying("bf55d24e465b3fb8dd1800b20fefff34", "es-AR", paginaALLamar, "AR");

        retrofitListener.enqueue(new Callback<ContenedorPeliculasRetrofit>() {
            @Override
            public void onResponse(Call<ContenedorPeliculasRetrofit> call, Response<ContenedorPeliculasRetrofit> response) {
                ContenedorPeliculasRetrofit contenedorPeliculasRetrofit = response.body();
                //escuchadorDeNumeroDePaginas.finish(contenedorPeliculasRetrofit.getTotal_pages());
                escuchadorDelControlador.finish(contenedorPeliculasRetrofit.getListaDePeliculas());
            }

            @Override
            public void onFailure(Call<ContenedorPeliculasRetrofit> call, Throwable t) {
                escuchadorDelControlador.finish(new ArrayList<Pelicula>());
            }
        });
    }


    public void getGenres(final ResultsListener<List<Genero>> escuchadorDeGenerosControlador) {

        Call<ContenedorDeGenerosRetrofit> retrofitListener = servicioPelicula.getGenreList("bf55d24e465b3fb8dd1800b20fefff34", "es-AR");

        retrofitListener.enqueue(new Callback<ContenedorDeGenerosRetrofit>() {
            @Override
            public void onResponse(Call<ContenedorDeGenerosRetrofit> call, Response<ContenedorDeGenerosRetrofit> response) {
                ContenedorDeGenerosRetrofit contenedorDeGenerosRetrofit = response.body();
                escuchadorDeGenerosControlador.finish(contenedorDeGenerosRetrofit.getGenres());
            }

            @Override
            public void onFailure(Call<ContenedorDeGenerosRetrofit> call, Throwable t) {
                escuchadorDeGenerosControlador.finish(new ArrayList<Genero>());
            }
        });

    }

    public void getTrailer(final ResultsListener<String> escuchadorDeKeyControlador, Integer idPelicula) {
        Call<ContenedorDeTrailersRetrofit> retrofitListener = servicioPelicula.getKey(idPelicula, "bf55d24e465b3fb8dd1800b20fefff34");


        retrofitListener.enqueue(new Callback<ContenedorDeTrailersRetrofit>() {
            @Override
            public void onResponse(Call<ContenedorDeTrailersRetrofit> call, Response<ContenedorDeTrailersRetrofit> response) {
                ContenedorDeTrailersRetrofit contenedorDeTrailersRetrofit = response.body();

                List<Trailer> listaDeTrailers = contenedorDeTrailersRetrofit.getListaDeTrailers();

                String keyDePelicula = listaDeTrailers.get(0).getKey();
                escuchadorDeKeyControlador.finish(keyDePelicula);
            }

            @Override
            public void onFailure(Call<ContenedorDeTrailersRetrofit> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
