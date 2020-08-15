package com.java.music.fragment.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.java.music.R;
import com.java.music.activity.FilmDetailActivity;
import com.java.music.activity.SongDetailActivity;
import com.java.music.adapter.film.FilmNewHomeAdapter;
import com.java.music.adapter.film.FilmSuggrestionHomeAdapter;
import com.java.music.adapter.song.SongHomeAdapter;
import com.java.music.adapter.song.SongRandomHomeAdapter;
import com.java.music.adapter.song.SongSuggressHomeAdapter;
import com.java.music.api.APIService;
import com.java.music.api.APIUntil;
import com.java.music.model.Token;
import com.java.music.model.film.FilmEntityModel;
import com.java.music.model.song.SongEntityModel;

import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.java.music.activity.MainActivity.MEDIAPLAYER;

public class HomeFragment extends Fragment {

    Token token = new Token();

    APIService apiService;
    RecyclerView rc_songrandom, rc_musicrank, rc_filmnew, rc_actor_hot, rc_film_goiy, rc_song_goiy;
    CardView card_nghegi, card_musicrank, card_filmnew, card_actor_hot, card_film_goiy, card_song_goiy;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_home, container, false);

        apiService = APIUntil.getServer();

        addControls(view);

        loadSongRandom();
        loadSongHome();
        loadFilmNewHome();
        loadFilmSugges();
        loadSongSuggress();

        return view;
    }

    private void addControls(View view) {
        rc_songrandom = view.findViewById(R.id.rc_songrandom);
        rc_musicrank = view.findViewById(R.id.rc_musicrank);
        rc_filmnew = view.findViewById(R.id.rc_filmnew);
        rc_actor_hot = view.findViewById(R.id.rc_actor_hot);
        rc_film_goiy = view.findViewById(R.id.rc_film_goiy);
        rc_song_goiy = view.findViewById(R.id.rc_song_goiy);
        card_nghegi = view.findViewById(R.id.card_nghegi);
        card_musicrank = view.findViewById(R.id.card_musicrank);
        card_filmnew = view.findViewById(R.id.card_filmnew);
        card_actor_hot = view.findViewById(R.id.card_actor_hot);
        card_song_goiy = view.findViewById(R.id.card_song_goiy);
        card_film_goiy = view.findViewById(R.id.card_film_goiy);

    }

    private void loadSongSuggress() {
        token.setApiToken("81799789AE3A4D0C8ABEE22023622522");
        apiService.getAllSong(token).enqueue(new Callback<List<SongEntityModel>>() {
            @Override
            public void onResponse(Call<List<SongEntityModel>> call, Response<List<SongEntityModel>> response) {
                if (response.isSuccessful()) {
                    card_song_goiy.setVisibility(View.VISIBLE);
                    rc_song_goiy.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                    SongSuggressHomeAdapter suggressHomeAdapter = new SongSuggressHomeAdapter(getContext(), response.body());
                    rc_song_goiy.setAdapter(suggressHomeAdapter);
                    suggressHomeAdapter.notifyDataSetChanged();
                    suggressHomeAdapter.setListener(model -> {
                        if (model != null) {

                            if (MEDIAPLAYER != null) {
                                MEDIAPLAYER.stop();
                                MEDIAPLAYER.reset();
                                MEDIAPLAYER.release();
                            }
                            MEDIAPLAYER = null;

                            String customURL = model.getSongEntity().getUploadsource();

                            if (isValid(customURL)) {
                                Intent intent = new Intent(getContext(), SongDetailActivity.class);
                                intent.putExtra("model", model.getSongEntity().getId());
//                                Bundle bundle = new Bundle();
//                                bundle.putSerializable("model", model.getSongEntity());
//                                intent.putExtras(bundle);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getContext(), "Link nhạc không tồn tại", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<SongEntityModel>> call, Throwable t) {
                Log.e("Er", t.getMessage());
            }
        });
    }

    private void loadFilmSugges() {
        token.setApiToken("81799789AE3A4D0C8ABEE22023622522");
        apiService.getAllFilm(token).enqueue(new Callback<List<FilmEntityModel>>() {
            @Override
            public void onResponse(Call<List<FilmEntityModel>> call, Response<List<FilmEntityModel>> response) {
                if (response.isSuccessful()) {
                    card_film_goiy.setVisibility(View.VISIBLE);
                    rc_film_goiy.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    FilmSuggrestionHomeAdapter filmSuggrestionHomeAdapter = new FilmSuggrestionHomeAdapter(getContext(), response.body());
                    rc_film_goiy.setAdapter(filmSuggrestionHomeAdapter);
                    filmSuggrestionHomeAdapter.notifyDataSetChanged();
                    filmSuggrestionHomeAdapter.setListener(model -> {
                        if (model != null) {
                            if (MEDIAPLAYER != null) {
                                MEDIAPLAYER.stop();
                                MEDIAPLAYER.reset();
                                MEDIAPLAYER.release();
                            }
                            MEDIAPLAYER = null;


                            String customURL = model.getFilmEntity().getUploadsource();

                            if (isValid(customURL)) {
                                Intent intent = new Intent(getContext(), FilmDetailActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("model", model);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getContext(), "Link phim không tồn tại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<FilmEntityModel>> call, Throwable t) {
                Log.e("Er", t.getMessage());
            }
        });
    }

    private void loadFilmNewHome() {
        token.setApiToken("81799789AE3A4D0C8ABEE22023622522");
        apiService.getAllFilm(token).enqueue(new Callback<List<FilmEntityModel>>() {
            @Override
            public void onResponse(Call<List<FilmEntityModel>> call, Response<List<FilmEntityModel>> response) {
                if (response.isSuccessful()) {
                    card_filmnew.setVisibility(View.VISIBLE);
                    rc_filmnew.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                    FilmNewHomeAdapter filmNewHomeAdapter = new FilmNewHomeAdapter(getContext(), response.body());
                    rc_filmnew.setAdapter(filmNewHomeAdapter);
                    filmNewHomeAdapter.notifyDataSetChanged();
                    filmNewHomeAdapter.setListener(model -> {
                        if (model != null) {
                            if (MEDIAPLAYER != null) {
                                MEDIAPLAYER.stop();
                                MEDIAPLAYER.reset();
                                MEDIAPLAYER.release();
                            }
                            MEDIAPLAYER = null;
                            String customURL = model.getFilmEntity().getUploadsource();

                            if (isValid(customURL)) {
                                Intent intent = new Intent(getContext(), FilmDetailActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("model", model);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getContext(), "Link phim không tồn tại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<FilmEntityModel>> call, Throwable t) {
                Log.e("Er", t.getMessage());
            }
        });
    }

    private void loadSongHome() {
        token.setApiToken("81799789AE3A4D0C8ABEE22023622522");
        apiService.getAllSong(token).enqueue(new Callback<List<SongEntityModel>>() {
            @Override
            public void onResponse(Call<List<SongEntityModel>> call, Response<List<SongEntityModel>> response) {
                if (response.isSuccessful()) {
                    card_musicrank.setVisibility(View.VISIBLE);
                    rc_musicrank.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    SongHomeAdapter songHomeAdapter = new SongHomeAdapter(getContext(), response.body());
                    rc_musicrank.setAdapter(songHomeAdapter);
                    songHomeAdapter.notifyDataSetChanged();
                    songHomeAdapter.setListener(model -> {
                        if (model != null) {

                            if (MEDIAPLAYER != null) {
                                MEDIAPLAYER.stop();
                                MEDIAPLAYER.reset();
                                MEDIAPLAYER.release();
                            }
                            MEDIAPLAYER = null;

                            String customURL = model.getSongEntity().getUploadsource();

                            if (isValid(customURL)) {
                                Intent intent = new Intent(getContext(), SongDetailActivity.class);
                                intent.putExtra("model", model.getSongEntity().getId());
//                                Bundle bundle = new Bundle();
//                                bundle.putSerializable("model", model.getSongEntity());
//                                intent.putExtras(bundle);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getContext(), "Link nhạc không tồn tại", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<SongEntityModel>> call, Throwable t) {
                Log.e("Er", t.getMessage());
            }
        });
    }

    private void loadSongRandom() {
        token.setApiToken("81799789AE3A4D0C8ABEE22023622522");
        apiService.getAllSong(token).enqueue(new Callback<List<SongEntityModel>>() {
            @Override
            public void onResponse(Call<List<SongEntityModel>> call, Response<List<SongEntityModel>> response) {
                if (response.isSuccessful()) {
                    card_nghegi.setVisibility(View.VISIBLE);
                    rc_songrandom.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    SongRandomHomeAdapter songRandomHomeAdapter = new SongRandomHomeAdapter(getContext(), response.body());
                    rc_songrandom.setAdapter(songRandomHomeAdapter);
                    songRandomHomeAdapter.notifyDataSetChanged();
                    songRandomHomeAdapter.setListener(model -> {
                        if (model != null) {

                            if (MEDIAPLAYER != null) {
                                MEDIAPLAYER.stop();
                                MEDIAPLAYER.reset();
                                MEDIAPLAYER.release();
                            }
                            MEDIAPLAYER = null;

                            String customURL = model.getSongEntity().getUploadsource();

                            if (isValid(customURL)) {
                                Intent intent = new Intent(getContext(), SongDetailActivity.class);
                                intent.putExtra("model", model.getSongEntity().getId());
//                                Bundle bundle = new Bundle();
//                                bundle.putSerializable("model", model.getSongEntity());
//                                intent.putExtras(bundle);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getContext(), "Link nhạc không tồn tại", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<SongEntityModel>> call, Throwable t) {
                Log.e("Er", t.getMessage());
            }
        });
    }

    public static boolean isValid(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}