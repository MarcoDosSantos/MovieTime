package com.example.yonoc.coverflow.Model.RoomDB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.yonoc.coverflow.Model.POJO.Genero;

import java.util.List;

/**
 * Created by yonoc on 7/21/2018.
 */

@Dao
public interface RoomServicePelicula {

    @Query("select * from PeliculaRoom")
    List<PeliculaRoom> getAllPeliculas();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertarPelicula(PeliculaRoom peliculaRoom);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertarGeneros(List<Genero> listGenero);

    @Query("select * from genero")
    List<Genero> getGenero();

    @Query("select * from favoritopeliculas")
    List<FavoritoPeliculas> getFavoritos();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertarFavorito(FavoritoPeliculas favoritoPeliculas);

    @Query("select * from favoritopeliculas where id like :id")
    FavoritoPeliculas getFavoritoConId(Integer id);

    @Delete
    void borrarFavorito(FavoritoPeliculas favoritoPeliculas);
}
