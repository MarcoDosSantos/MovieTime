package com.example.yonoc.coverflow.Model.RoomDB;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by yonoc on 7/21/2018.
 */

public class Converters {
    @TypeConverter

    public static ArrayList<String> fromString(String value) {

        Type listType = new TypeToken<ArrayList<String>>() {}.getType();

        return new Gson().fromJson(value, listType);

    }

    @TypeConverter

    public static String fromArrayLisr(ArrayList<String> list) {

        Gson gson = new Gson();

        String json = gson.toJson(list);

        return json;

    }
}
