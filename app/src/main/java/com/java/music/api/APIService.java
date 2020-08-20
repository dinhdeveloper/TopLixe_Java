package com.java.music.api;

import com.google.gson.JsonObject;
import com.java.music.model.CustomerModel;
import com.java.music.model.Token;
import com.java.music.model.UserEntityModel;
import com.java.music.model.actor.ActorEntityModel;
import com.java.music.model.film.FilmEntity;
import com.java.music.model.film.FilmEntityModel;
import com.java.music.model.singer.SingerEntityModel;
import com.java.music.model.song.AlbumEntityModel;
import com.java.music.model.song.SongEntity;
import com.java.music.model.song.SongEntityModel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface APIService {

    @Headers({
            "Content-Type: application/json",
            "userToken: aaa"
    })
    @POST("Lixe/api/MusicSite/Song/GetTop10/")
    Call<List<SongEntityModel>> getAllSong(@Body Token apiToken);

    @Headers({
            "Content-Type: application/json",
            "userToken: aaa"
    })
    @POST("Lixe/api/FilmSite/Film/GetTop10/")
    Call<List<FilmEntityModel>> getAllFilm(@Body Token apiToken);

    @Headers({
            "Content-Type: application/json",
            "userToken: aaa"
    })
    @POST("Lixe/api/FilmSite/Film/GetRandom10/")
    Call<List<FilmEntityModel>> getFilmRandom(@Body Token apiToken);

    @Headers({
            "Content-Type: application/json",
            "userToken: aaa"
    })
    @POST("Lixe/api/FilmSite/Film/GetAllHasPage{itemOnPage}/{page}")
    Call<List<FilmEntityModel>> getHasPageFilm(@Body Token apiToken,@Path("itemOnPage") int itemOnPage,@Path("page") int page);

    @Headers({
            "Content-Type: application/json",
            "userToken: aaa"
    })
    @POST("Lixe/api/FilmSite/Actor/GetAllHasPage{itemOnPage}/{page}")
    Call<List<ActorEntityModel>> getActor(@Body Token apiToken,@Path("itemOnPage") int itemOnPage, @Path("page") int page);

    @Headers({
            "Content-Type: application/json",
            "userToken: aaa"
    })
    @POST("/Lixe/api/MusicSite/Singer/GetAllHasPage{itemOnPage}/{page}")
    Call<List<SingerEntityModel>> getSingerPage(@Body Token apiToken,@Path("itemOnPage") int itemOnPage, @Path("page")int page);

    @Headers({
            "Content-Type: application/json",
            "userToken: aaa"
    })
    @POST("Lixe/api/MusicSite/Song/GetAllHasPage{itemOnPage}/{page}")
    Call<List<SongEntityModel>> getSongPage(@Body Token apiToken,@Path("itemOnPage") int itemOnPage, @Path("page") int page);


    @Headers({
            "Content-Type: application/json",
            "userToken: aaa"
    })
    @POST("Lixe/api/MusicSite/Album/GetTop10")
    Call<List<AlbumEntityModel>> getAllAlbum(@Body Token apiToken);

    @Headers({
            "Content-Type: application/json",
            "userToken: aaa"
    })
    @POST("Lixe/api/MusicSite/Song/GetDetail/{id}")
    Call<SongEntityModel> getSongDetail(@Body Token apiToken,@Path("id") int id);

    @Headers({
            "Content-Type: application/json",
            "userToken: aaa"
    })
    @POST("Lixe/api/Search/q/{key}/song")
    Call<List<SongEntityModel>> getSearchSong(@Body Token apiToken,@Path("key") String key);

    @Headers({
            "Content-Type: application/json",
            "userToken: aaa"
    })
    @POST("Lixe/api/Search/q/{key}/film")
    Call<List<FilmEntityModel>> getSearchFilm(@Body Token apiToken, @Path("key") String key);

    @Headers({
            "Content-Type: application/json",
            "userToken: aaa"
    })
    @POST("Lixe/api/Account/Login")
    Call<CustomerModel> login(@Body UserEntityModel model);

}
