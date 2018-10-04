package com.example.yonoc.coverflow.Model.RoomDB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.example.yonoc.coverflow.Model.POJO.Genero;


/**
 * Created by yonoc on 7/21/2018.
 */

@Database(entities = {PeliculaRoom.class, Genero.class, FavoritoPeliculas.class}, version = 3)
//@TypeConverters({Converters.class})
public abstract class RoomAbstractDatabase extends RoomDatabase {
    public abstract RoomServicePelicula peliculaRoomDAO();
}
