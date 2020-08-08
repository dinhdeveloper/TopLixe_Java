package com.java.music.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.java.music.R;
import com.java.music.adapter.film.FilmHasPageAdapter;
import com.java.music.api.APIService;
import com.java.music.api.APIUntil;
import com.java.music.common.Const;
import com.java.music.fragment.film.FilmDetailFragment;
import com.java.music.model.film.FilmEntityModel;

import java.util.List;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.java.music.common.Const.HOST_MUSIC;
import static com.java.music.fragment.home.HomeFragment.isValid;

public class FilmDetailActivity extends AppCompatActivity {

    APIService apiService;

    JZVideoPlayerStandard videoplayer;
    RecyclerView rc_overlap;
    ImageView onBack;
    FilmEntityModel entityModel;
    TextView txtNameFilm, txtDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);
        apiService = APIUntil.getServer();
        addControls();
        addEvent();
        getDataMore();
    }

    private void addControls() {
        videoplayer = findViewById(R.id.videoplayer);
        rc_overlap = findViewById(R.id.rc_overlap);
        txtDescription = findViewById(R.id.txtDescription);
        txtNameFilm = findViewById(R.id.txtNameFilm);
        onBack = findViewById(R.id.onBack);
    }

    @Override
    public void onPause() {
        super.onPause();
        JZVideoPlayer.SAVE_PROGRESS = false;
        JZVideoPlayer.releaseAllVideos();
    }
    private void getDataMore() {
        apiService.getHasPageFilm(20, 0).enqueue(new Callback<List<FilmEntityModel>>() {
            @Override
            public void onResponse(Call<List<FilmEntityModel>> call, Response<List<FilmEntityModel>> response) {
                if (response.isSuccessful()) {
                    //card_hot.setVisibility(View.VISIBLE);
                    rc_overlap.setLayoutManager(new LinearLayoutManager(FilmDetailActivity.this, LinearLayoutManager.VERTICAL, false));
                    FilmHasPageAdapter songRandomHomeAdapter = new FilmHasPageAdapter(FilmDetailActivity.this, response.body());
                    rc_overlap.setAdapter(songRandomHomeAdapter);
                    songRandomHomeAdapter.notifyDataSetChanged();
                    songRandomHomeAdapter.setListener(model -> {
                        if (model != null) {

                            String customURL = Const.HOST_MUSIC+ model.getFilmEntity().getUploadsource();

                            if (isValid(customURL)) {
                                Intent intent = new Intent(getApplicationContext(), FilmDetailActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("model",model);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Link phim không tồn tại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    songRandomHomeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<FilmEntityModel>> call, Throwable t) {
                Log.e("Er", t.getMessage());
            }
        });
    }

    private void addEvent() {
        Intent intent = getIntent();
        FilmEntityModel entityModel = (FilmEntityModel)intent.getExtras().get("model");
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
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
}