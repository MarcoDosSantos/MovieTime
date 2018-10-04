package com.example.yonoc.coverflow.Model.RoomDB;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.yonoc.coverflow.Model.POJO.Genero;

import java.util.List;


/**
 * Created by yonoc on 7/21/2018.
 */

public class RoomDAO {
    private RoomAbstractDatabase roomDatabase;


    public RoomDAO(Context context){
        roomDatabase = Room.databaseBuilder(context,
                RoomAbstractDatabase.class, "peliculas_room")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }


    public List<PeliculaRoom> getAllPeliculas(){
        List<PeliculaRoom> list = roomDatabase.peliculaRoomDAO().getAllPeliculas();
        return list;
    }

    public void insertarPelicula(PeliculaRoom pelicula){
        roomDatabase.peliculaRoomDAO().insertarPelicula(pelicula);
    }


    public void insertarGeneros (List<Genero> generoList){
        roomDatabase.peliculaRoomDAO().insertarGeneros(generoList);
    }


    public List<Genero> getGenero(){
        List<Genero> list = roomDatabase.peliculaRoomDAO().getGenero();
        return list;
    }

    public List<FavoritoPeliculas> getFavoritos() {
        List<FavoritoPeliculas> list = roomDatabase.peliculaRoomDAO().getFavoritos();
        return list;
    }

    public void insertarFavorito(FavoritoPeliculas favoritoPeliculas) {
        roomDatabase.peliculaRoomDAO().insertarFavorito(favoritoPeliculas);
    }

    public FavoritoPeliculas getFavoritoConId(Integer id){
        return roomDatabase.peliculaRoomDAO().getFavoritoConId(id);
    }

    public void borrarFavorito(FavoritoPeliculas favoritoPeliculas){
        roomDatabase.peliculaRoomDAO().borrarFavorito(favoritoPeliculas);
    }


}
