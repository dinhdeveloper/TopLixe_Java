package com.java.music.api;

import com.java.music.model.actor.ActorEntityModel;
import com.java.music.model.film.FilmEntityModel;
import com.java.music.model.singer.SingerEntityModel;
import com.java.music.model.song.AlbumEntityModel;
import com.java.music.model.song.SongEntityModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIService {
    @GET("Lixe/api/MusicSite/Song/GetTop10/")
    Call<List<SongEntityModel>> getAllSong();

    @GET("Lixe/api/FilmSite/Film/GetTop10/")
    Call<List<FilmEntityModel>> getAllFilm();

    @GET("Lixe/api/FilmSite/Film/GetRandom10/")
    Call<List<FilmEntityModel>> getFilmRandom();

    @GET("Lixe/api/FilmSite/Film/GetAllHasPage{itemOnPage}/{page}")
    Call<List<FilmEntityModel>> getHasPageFilm(@Path("itemOnPage") int itemOnPage,@Path("page") int page);

    @GET("Lixe/api/FilmSite/Actor/GetAllHasPage{itemOnPage}/{page}")
    Call<List<ActorEntityModel>> getActor(@Path("itemOnPage") int itemOnPage, @Path("page") int page);

    @GET("/Lixe/api/MusicSite/Singer/GetAllHasPage{itemOnPage}/{page}")
    Call<List<SingerEntityModel>> getSingerPage(@Path("itemOnPage") int itemOnPage, @Path("page") int page);

    @GET("Lixe/api/MusicSite/Song/GetAllHasPage{itemOnPage}/{page}")
    Call<List<SongEntityModel>> getSongPage(@Path("itemOnPage") int itemOnPage, @Path("page") int page);


    @GET("Lixe/api/MusicSite/Album/GetTop10")
    Call<List<AlbumEntityModel>> getAllAlbum();

    @GET("Lixe/api/MusicSite/Song/GetDetail/{id}")
    Call<SongEntityModel> getSongDetail(@Path("id") int id);


}
