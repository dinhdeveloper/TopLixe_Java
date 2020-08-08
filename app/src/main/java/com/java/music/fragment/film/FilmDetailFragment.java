package com.java.music.fragment.film;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.java.music.R;
import com.java.music.adapter.film.FilmHasPageAdapter;
import com.java.music.api.APIService;
import com.java.music.api.APIUntil;
import com.java.music.model.film.FilmEntityModel;

import java.util.List;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.java.music.common.Const.HOST_MUSIC;

public class FilmDetailFragment extends Fragment {

    APIService apiService;

    JZVideoPlayerStandard videoplayer;
    RecyclerView rc_overlap;
    ImageView onBack;
    FilmEntityModel entityModel;
    TextView txtNameFilm, txtDescription;

    public FilmDetailFragment() {
        // Required empty public constructor
    }

    public static FilmDetailFragment newInstance(FilmEntityModel model) {
        FilmDetailFragment fragment = new FilmDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("model", model);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            entityModel = (FilmEntityModel) getArguments().get("model");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_film_detail, container, false);
        apiService = APIUntil.getServer();
        addControls(view);
        addEvent();
        getDataMore();

        return view;
    }

    private void getDataMore() {
        apiService.getHasPageFilm(20, 0).enqueue(new Callback<List<FilmEntityModel>>() {
            @Override
            public void onResponse(Call<List<FilmEntityModel>> call, Response<List<FilmEntityModel>> response) {
                if (response.isSuccessful()) {
                    //card_hot.setVisibility(View.VISIBLE);
                    rc_overlap.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                    FilmHasPageAdapter songRandomHomeAdapter = new FilmHasPageAdapter(getContext(), response.body());
                    rc_overlap.setAdapter(songRandomHomeAdapter);
                    songRandomHomeAdapter.notifyDataSetChanged();
                    songRandomHomeAdapter.setListener(model -> {
                        if (model != null) {
                            FilmDetailFragment fragment = FilmDetailFragment.newInstance(model);
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.add(R.id.mainActivity, fragment, "F1");
                            transaction.commit();
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

    private void addEvent() {
        if (entityModel != null) {
            String url = HOST_MUSIC + entityModel.getFilmEntity().getUploadsource();
            videoplayer.setUp(url , JZVideoPlayerStandard.SCREEN_STATE_OFF, entityModel.getFilmEntity().getFilmname());

            txtNameFilm.setText(entityModel.getFilmEntity().getFilmname());
            txtDescription.setText(entityModel.getFilmEntity().getInfo());

            videoplayer.startButton.performClick();
            videoplayer.startVideo();
            JZVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
            JZVideoPlayer.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
            JZVideoPlayer.SAVE_PROGRESS = false;
        } else {
            JZVideoPlayer.SAVE_PROGRESS = false;
            getActivity().finish();
        }
    }

    private void addControls(View view) {
        videoplayer = view.findViewById(R.id.videoplayer);
        rc_overlap = view.findViewById(R.id.rc_overlap);
        txtDescription = view.findViewById(R.id.txtDescription);
        txtNameFilm = view.findViewById(R.id.txtNameFilm);
        onBack = view.findViewById(R.id.onBack);
    }

    @Override
    public void onPause() {
        super.onPause();
        JZVideoPlayer.SAVE_PROGRESS = false;
        JZVideoPlayer.releaseAllVideos();
    }
}