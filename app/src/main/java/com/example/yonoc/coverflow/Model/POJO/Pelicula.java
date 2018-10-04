package com.example.yonoc.coverflow.Model.POJO;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yonoc on 5/22/2018.
 */

public class Pelicula {


    public String title, overview, release_date, backdrop_path;
    @SerializedName("poster_path")
    public String url;
    public Integer id;
    public Integer vote_count;
    public Number vote_average;
    public List<Integer> genre_ids = new ArrayList<>();



    public Pelicula(String title, String overview, String release_date, String url, Integer id, Integer vote_count, Number vote_average, String backdrop_path, List<Integer> genre_ids) {
        this.title = title;
        this.overview = overview;
        this.release_date = release_date;
        this.url = url;
        this.id = id;
        this.vote_count = vote_count;
        this.vote_average = vote_average;
        this.backdrop_path = backdrop_path;
        this.genre_ids = genre_ids;

    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getUrl() {
        return url;
    }

    public Integer getId() {
        return id;
    }

    public Integer getVote_count() {
        return vote_count;
    }

    public Number getVote_average() {
        return vote_average;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
