package com.example.yonoc.coverflow.Model.RoomDB;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yonoc on 7/21/2018.
 */


@Entity
public class FavoritoPeliculas {


    public String title, overview, release_date, backdrop_path;
    @SerializedName("poster_path")
    public String url;
    @PrimaryKey
    @NonNull
    public Integer id;
    public Integer vote_count;
    public Integer vote_average;
    public Integer genre_ids;


    public FavoritoPeliculas(String title, String overview, String release_date, String backdrop_path, String url, @NonNull Integer id, Integer vote_count, Integer vote_average, Integer genre_ids) {
        this.title = title;
        this.overview = overview;
        this.release_date = release_date;
        this.backdrop_path = backdrop_path;
        this.url = url;
        this.id = id;
        this.vote_count = vote_count;
        this.vote_average = vote_average;
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

    public Integer getGenre_ids() {
        return genre_ids;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setVote_count(Integer vote_count) {
        this.vote_count = vote_count;
    }

    public void setVote_average(Integer vote_average) {
        this.vote_average = vote_average;
    }

    public void setGenre_ids(Integer genre_ids) {
        this.genre_ids = genre_ids;
    }

}