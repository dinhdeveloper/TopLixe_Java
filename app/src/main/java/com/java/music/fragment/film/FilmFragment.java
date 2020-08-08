package com.java.music.fragment.film;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import com.java.music.adapter.actor.FilmActorAdapter;
import com.java.music.adapter.film.FilmHasPageAdapter;
import com.java.music.adapter.film.FilmSuggrestionHomeAdapter;
import com.java.music.api.APIService;
import com.java.music.api.APIUntil;
import com.java.music.common.Const;
import com.java.music.fragment.film.FilmDetailFragment;
import com.java.music.model.actor.ActorEntity;
import com.java.music.model.actor.ActorEntityModel;
import com.java.music.model.film.FilmEntityModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.java.music.activity.MainActivity.MEDIAPLAYER;
import static com.java.music.fragment.home.HomeFragment.isValid;

public class FilmFragment extends Fragment {

    APIService apiService;

    CardView card_hot, card_recommend, card_actor;
    RecyclerView rc_listHot, rc_listSuggest, rc_actor, rc_listFilm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_film, container, false);

        apiService = APIUntil.getServer();
        addControls(view);

        callFilmHot();
        callGoiYChoBan();
        callActor();
        callFilmHasPage();

        return view;
    }

    private void callFilmHasPage() {
        apiService.getHasPageFilm(10, 0).enqueue(new Callback<List<FilmEntityModel>>() {
            @Override
            public void onResponse(Call<List<FilmEntityModel>> call, Response<List<FilmEntityModel>> response) {
                if (response.isSuccessful()) {
                    //card_hot.setVisibility(View.VISIBLE);
                    rc_listFilm.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                    FilmHasPageAdapter songRandomHomeAdapter = new FilmHasPageAdapter(getContext(), response.body());
                    rc_listFilm.setAdapter(songRandomHomeAdapter);
                    songRandomHomeAdapter.notifyDataSetChanged();
                    songRandomHomeAdapter.setListener(model -> {
                        if (model != null) {
                            if (MEDIAPLAYER != null) {
                                MEDIAPLAYER.stop();
                                MEDIAPLAYER.reset();
                                MEDIAPLAYER.release();
                            }
                            MEDIAPLAYER = null;
                            String customURL = Const.HOST_MUSIC+ model.getFilmEntity().getUploadsource();

                            if (isValid(customURL)) {
                                Intent intent = new Intent(getContext(), FilmDetailActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("model",model);
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

    private void callActor() {
        apiService.getActor(10, 0).enqueue(new Callback<List<ActorEntityModel>>() {
            @Override
            public void onResponse(Call<List<ActorEntityModel>> call, Response<List<ActorEntityModel>> response) {
                if (response.isSuccessful()) {
                    card_actor.setVisibility(View.VISIBLE);
                    rc_actor.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                    FilmActorAdapter songRandomHomeAdapter = new FilmActorAdapter(getContext(), response.body());
                    rc_actor.setAdapter(songRandomHomeAdapter);
                    songRandomHomeAdapter.notifyDataSetChanged();
                    songRandomHomeAdapter.setListener(model -> {
                        Intent intent = new Intent(getContext(), ActorEntity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("model",model);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    });
                }
            }

            @Override
            public void onFailure(Call<List<ActorEntityModel>> call, Throwable t) {
                Log.e("Ex", t.getMessage());
            }
        });
    }

    private void callGoiYChoBan() {
        apiService.getAllFilm().enqueue(new Callback<List<FilmEntityModel>>() {
            @Override
            public void onResponse(Call<List<FilmEntityModel>> call, Response<List<FilmEntityModel>> response) {
                if (response.isSuccessful()) {
                    card_recommend.setVisibility(View.VISIBLE);
                    rc_listSuggest.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    FilmSuggrestionHomeAdapter songRandomHomeAdapter = new FilmSuggrestionHomeAdapter(getContext(), response.body());
                    rc_listSuggest.setAdapter(songRandomHomeAdapter);
                    songRandomHomeAdapter.notifyDataSetChanged();
                    songRandomHomeAdapter.setListener(model -> {
                        if (model != null) {
                            if (MEDIAPLAYER != null) {
                                MEDIAPLAYER.stop();
                                MEDIAPLAYER.reset();
                                MEDIAPLAYER.release();
                            }
                            MEDIAPLAYER = null;
                            String customURL = Const.HOST_MUSIC+ model.getFilmEntity().getUploadsource();

                            if (isValid(customURL)) {
                                Intent intent = new Intent(getContext(), FilmDetailActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("model",model);
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

    private void callFilmHot() {
        apiService.getAllFilm().enqueue(new Callback<List<FilmEntityModel>>() {
            @Override
            public void onResponse(Call<List<FilmEntityModel>> call, Response<List<FilmEntityModel>> response) {
                if (response.isSuccessful()) {
                    card_hot.setVisibility(View.VISIBLE);
                    rc_listHot.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                    FilmSuggrestionHomeAdapter filmSuggrestionHomeAdapter = new FilmSuggrestionHomeAdapter(getContext(), response.body());
                    rc_listHot.setAdapter(filmSuggrestionHomeAdapter);
                    filmSuggrestionHomeAdapter.notifyDataSetChanged();
                    filmSuggrestionHomeAdapter.setListener(model -> {
                        if (model != null) {
                            if (MEDIAPLAYER != null) {
                                MEDIAPLAYER.stop();
                                MEDIAPLAYER.reset();
                                MEDIAPLAYER.release();
                            }
                            MEDIAPLAYER = null;
                            String customURL = Const.HOST_MUSIC+ model.getFilmEntity().getUploadsource();

                            if (isValid(customURL)) {
                                Intent intent = new Intent(getContext(), FilmDetailActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("model",model);
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

    private void addControls(View view) {
        card_hot = view.findViewById(R.id.card_hot);
        card_recommend = view.findViewById(R.id.card_recommend);
        card_actor = view.findViewById(R.id.card_actor);
        rc_listHot = view.findViewById(R.id.rc_listHot);
        rc_listSuggest = view.findViewById(R.id.rc_listSuggest);
        rc_actor = view.findViewById(R.id.rc_actor);
        rc_listFilm = view.findViewById(R.id.rc_listFilm);
    }
}