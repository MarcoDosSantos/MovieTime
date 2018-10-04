package com.example.yonoc.coverflow.Model.DAO.RetrofitServices;

import com.example.yonoc.coverflow.Model.POJO.ContenedoresRetrofit.ContenedorDeGenerosRetrofit;
import com.example.yonoc.coverflow.Model.POJO.ContenedoresRetrofit.ContenedorDeTrailersRetrofit;
import com.example.yonoc.coverflow.Model.POJO.Trailer;
import com.example.yonoc.coverflow.Model.POJO.ContenedoresRetrofit.ContenedorPeliculasRetrofit;
import com.example.yonoc.coverflow.Model.POJO.Pelicula;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by yonoc on 6/9/2018.
 */

public interface ServicioPelicula {

/*    @GET("/sites/MLA/search")
    Call<ContenedorPeliculasRetrofit> getPeliculas(@Query("q") String películasABuscar);*/

    @GET("3/movie/{movieId}")
    Call<Pelicula> getMovieDetail(@Path("movieId") String movieId,
                                  @Query("api_key") String apiKey,
                                  @Query("language") String language);

    @GET("3/genre/list")
    Call<ContenedorDeGenerosRetrofit> getGenreList(@Query("api_key") String apiKey,
                                                   @Query("language") String language);

    @GET("3/movie/popular")
    Call<ContenedorPeliculasRetrofit> getPopularMovies(@Query("api_key") String apiKey,
                                                       @Query("language") String language,
                                                       @Query("page") Integer page);

    @GET("3/movie/now_playing")
    Call<ContenedorPeliculasRetrofit> getNowPlaying(@Query("api_key") String apiKey,
                                                    @Query("language") String language,
                                                    @Query("page") Integer page,
                                                    @Query("Region") String region);

    @GET("3/movie/{movieId}/videos")
    Call<ContenedorDeTrailersRetrofit> getKey(@Path("movieId") Integer movieId,
                                              @Query("api_key") String apiKey) ;

}