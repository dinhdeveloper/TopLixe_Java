package com.java.music.fragment.song;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.java.music.R;
import com.java.music.activity.AlbumActivity;
import com.java.music.activity.FilmDetailActivity;
import com.java.music.activity.MainActivity;
import com.java.music.activity.SingerActivity;
import com.java.music.activity.SongDetailActivity;
import com.java.music.adapter.singer.SingerPageSongAdapter;
import com.java.music.adapter.song.AlbumSongAdapter;
import com.java.music.adapter.song.SongNewApdapter;
import com.java.music.adapter.song.SongSuggressHomeAdapter;
import com.java.music.api.APIService;
import com.java.music.api.APIUntil;
import com.java.music.common.Const;
import com.java.music.model.singer.SingerEntityModel;
import com.java.music.model.song.AlbumEntityModel;
import com.java.music.model.song.SongEntityModel;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.java.music.activity.MainActivity.MEDIAPLAYER;


public class SongFragment extends Fragment {

    APIService apiService;

    CardView card_hot, card_singer, card_album, card_singer_goiy;
    RecyclerView rc_listHot, rc_SingerPage, rc_album, rc_Singer_GoiY;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_song, container, false);

        apiService = APIUntil.getServer();
        addControls(view);

        getSongTop10();
        getAllAlbum();
        getPageSinger();
        loadSongSuggress();

        return view;
    }

    private void getSongTop10() {
        apiService.getAllSong().enqueue(new Callback<List<SongEntityModel>>() {
            @Override
            public void onResponse(Call<List<SongEntityModel>> call, Response<List<SongEntityModel>> response) {
                if (response.isSuccessful()) {
                    card_hot.setVisibility(View.VISIBLE);
                    rc_listHot.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                    SongNewApdapter songRandomHomeAdapter = new SongNewApdapter(getContext(), response.body());
                    rc_listHot.setAdapter(songRandomHomeAdapter);
                    songRandomHomeAdapter.notifyDataSetChanged();
                    songRandomHomeAdapter.setListener(model -> {
                        if (model != null) {

                            if (MEDIAPLAYER != null) {
                                MEDIAPLAYER.stop();
                                MEDIAPLAYER.reset();
                                MEDIAPLAYER.release();
                            }
                            MEDIAPLAYER = null;
                            String customURL = Const.HOST_MUSIC+ model.getSongEntity().getUploadsource();

                            if (isValid(customURL)) {
                                Intent intent = new Intent(getContext(), SongDetailActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("model", model.getSongEntity());
                                intent.putExtras(bundle);
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

    private void getAllAlbum() {
        apiService.getAllAlbum().enqueue(new Callback<List<AlbumEntityModel>>() {
            @Override
            public void onResponse(Call<List<AlbumEntityModel>> call, Response<List<AlbumEntityModel>> response) {
                if (response.isSuccessful()) {
                    card_album.setVisibility(View.VISIBLE);
                    rc_album.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                    AlbumSongAdapter songRandomHomeAdapter = new AlbumSongAdapter(getContext(), response.body());
                    rc_album.setAdapter(songRandomHomeAdapter);
                    songRandomHomeAdapter.notifyDataSetChanged();
                    songRandomHomeAdapter.setListener(model -> {
                        if (model.getAlbumEntity() != null) {
                            Intent intent = new Intent(getContext(), AlbumActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("album", model);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<AlbumEntityModel>> call, Throwable t) {
                Log.e("Er", t.getMessage());
            }
        });
    }

    private void getPageSinger() {
        apiService.getSingerPage(10, 0).enqueue(new Callback<List<SingerEntityModel>>() {
            @Override
            public void onResponse(Call<List<SingerEntityModel>> call, Response<List<SingerEntityModel>> response) {
                if (response.isSuccessful()) {
                    card_singer.setVisibility(View.VISIBLE);
                    rc_SingerPage.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                    SingerPageSongAdapter songRandomHomeAdapter = new SingerPageSongAdapter(getContext(), response.body());
                    rc_SingerPage.setAdapter(songRandomHomeAdapter);
                    songRandomHomeAdapter.notifyDataSetChanged();
                    songRandomHomeAdapter.setListener(model -> {
                        if (model.getSongDTOList() != null) {
                            Intent intent = new Intent(getContext(), SingerActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("singer", model);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getContext(), "Không có bài hát nào", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<SingerEntityModel>> call, Throwable t) {
                Log.e("Er", t.getMessage());
            }
        });
    }

    private void loadSongSuggress() {
        apiService.getSongPage(20, 0).enqueue(new Callback<List<SongEntityModel>>() {
            @Override
            public void onResponse(Call<List<SongEntityModel>> call, Response<List<SongEntityModel>> response) {
                if (response.isSuccessful()) {
                    card_singer_goiy.setVisibility(View.VISIBLE);
                    rc_Singer_GoiY.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                    SongSuggressHomeAdapter songRandomHomeAdapter = new SongSuggressHomeAdapter(getContext(), response.body());
                    rc_Singer_GoiY.setAdapter(songRandomHomeAdapter);
                    songRandomHomeAdapter.notifyDataSetChanged();
                    songRandomHomeAdapter.setListener(model -> {
                        if (model != null) {

                            if (MEDIAPLAYER != null) {
                                MEDIAPLAYER.stop();
                                MEDIAPLAYER.reset();
                                MEDIAPLAYER.release();
                            }
                            MEDIAPLAYER = null;

                            String customURL = Const.HOST_MUSIC+ model.getSongEntity().getUploadsource();

                            if (isValid(customURL)) {
                                Intent intent = new Intent(getContext(), SongDetailActivity.class);
                                intent.putExtra("model",model.getSongEntity().getId());
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
    public static boolean isValid(String url)
    {
        try {
            new URL(url).toURI();
            return true;
        }

        catch (Exception e) {
            return false;
        }
    }

    private void addControls(View view) {
        card_hot = view.findViewById(R.id.card_hot);
        card_singer = view.findViewById(R.id.card_singer);
        card_album = view.findViewById(R.id.card_album);
        card_singer_goiy = view.findViewById(R.id.card_singer_goiy);
        rc_listHot = view.findViewById(R.id.rc_listHot);
        rc_SingerPage = view.findViewById(R.id.rc_SingerPage);
        rc_album = view.findViewById(R.id.rc_album);
        rc_Singer_GoiY = view.findViewById(R.id.rc_Singer_GoiY);
    }
}