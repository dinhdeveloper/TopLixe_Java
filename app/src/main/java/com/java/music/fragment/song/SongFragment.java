package com.java.music.fragment.song;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.java.music.R;
import com.java.music.activity.AlbumActivity;
import com.java.music.activity.FilmDetailActivity;
import com.java.music.activity.MainActivity;
import com.java.music.activity.SingerActivity;
import com.java.music.activity.SongDetailActivity;
import com.java.music.adapter.singer.SingerPageSongAdapter;
import com.java.music.adapter.song.AlbumSongAdapter;
import com.java.music.adapter.song.SongNewApdapter;
import com.java.music.adapter.song.SongSearchAdapter;
import com.java.music.adapter.song.SongSuggressHomeAdapter;
import com.java.music.api.APIService;
import com.java.music.api.APIUntil;
import com.java.music.common.Const;
import com.java.music.model.Token;
import com.java.music.model.singer.SingerEntityModel;
import com.java.music.model.song.AlbumEntityModel;
import com.java.music.model.song.SongEntity;
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
    ImageView img_search, imvCloseSearch;
    EditText edtSearchSong;
    LinearLayout layoutSearch;
    RecyclerView listResultSearch;
    NestedScrollView layoutSong;

    CardView card_hot, card_singer, card_album, card_singer_goiy;
    RecyclerView rc_listHot, rc_SingerPage, rc_album, rc_Singer_GoiY;

    Token token = new Token();

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
        onClick();

        return view;
    }

    private void onClick() {
        img_search.setOnClickListener(v -> {
            layoutSearch.setVisibility(View.VISIBLE);
            img_search.setVisibility(View.GONE);
            edtSearchSong.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                    if (edtSearchSong.getText().toString() != null && !edtSearchSong.getText().toString().isEmpty()) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            searchSong(edtSearchSong.getText().toString());
                            return true;
                        }
                    }
                    Toast.makeText(getContext(), "Không có kết quả tìm kiếm!", Toast.LENGTH_SHORT).show();
                    return false;
                }
            });

            imvCloseSearch.setOnClickListener(v1 -> {
                layoutSearch.setVisibility(View.GONE);
                img_search.setVisibility(View.VISIBLE);

                listResultSearch.setVisibility(View.GONE);
                layoutSong.setVisibility(View.VISIBLE);
            });
        });
    }

    private void searchSong(String search) {
        if (search != null) {
            token.setApiToken("81799789AE3A4D0C8ABEE22023622522");
            apiService.getSearchSong(token, search).enqueue(new Callback<List<SongEntityModel>>() {
                @Override
                public void onResponse(Call<List<SongEntityModel>> call, Response<List<SongEntityModel>> response) {
                    if (response.isSuccessful()) {
                        if (!response.body().isEmpty()){
                            listResultSearch.setVisibility(View.VISIBLE);
                            layoutSong.setVisibility(View.GONE);
                            SongSearchAdapter adapter = new SongSearchAdapter(response.body(), getContext());
                            listResultSearch.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                            listResultSearch.setAdapter(adapter);
                            listResultSearch.setHasFixedSize(true);
                            adapter.notifyDataSetChanged();
                            adapter.setListenner(entity -> {
                                if (entity != null) {

                                    if (MEDIAPLAYER != null) {
                                        MEDIAPLAYER.stop();
                                        MEDIAPLAYER.reset();
                                        MEDIAPLAYER.release();
                                    }
                                    MEDIAPLAYER = null;

                                    String customURL = entity.getSongEntity().getUploadsource();

                                    if (isValid(customURL)) {
                                        Intent intent = new Intent(getContext(), SongDetailActivity.class);
                                        intent.putExtra("model", entity.getSongEntity().getId());
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(getContext(), "Link nhạc không tồn tại", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                        }else {
                            Toast.makeText(getContext(), "Không tìm thấy bài hát", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<SongEntityModel>> call, Throwable t) {
                    Log.e("onFailure", t.getMessage());
                }
            });
        }
    }

    private void getSongTop10() {
        token.setApiToken("81799789AE3A4D0C8ABEE22023622522");
        apiService.getAllSong(token).enqueue(new Callback<List<SongEntityModel>>() {
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

    private void getAllAlbum() {
        token.setApiToken("81799789AE3A4D0C8ABEE22023622522");
        apiService.getAllAlbum(token).enqueue(new Callback<List<AlbumEntityModel>>() {
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
        token.setApiToken("81799789AE3A4D0C8ABEE22023622522");
        apiService.getSingerPage(token, 10, 0).enqueue(new Callback<List<SingerEntityModel>>() {
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
        token.setApiToken("81799789AE3A4D0C8ABEE22023622522");
        apiService.getSongPage(token, 20, 0).enqueue(new Callback<List<SongEntityModel>>() {
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

                            String customURL = model.getSongEntity().getUploadsource();
                            //Toast.makeText(getContext(), ""+customURL, Toast.LENGTH_SHORT).show();

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

    private void addControls(View view) {
        card_hot = view.findViewById(R.id.card_hot);
        card_singer = view.findViewById(R.id.card_singer);
        card_album = view.findViewById(R.id.card_album);
        card_singer_goiy = view.findViewById(R.id.card_singer_goiy);
        rc_listHot = view.findViewById(R.id.rc_listHot);
        rc_SingerPage = view.findViewById(R.id.rc_SingerPage);
        rc_album = view.findViewById(R.id.rc_album);
        rc_Singer_GoiY = view.findViewById(R.id.rc_Singer_GoiY);
        edtSearchSong = view.findViewById(R.id.edtSearchSong);
        img_search = view.findViewById(R.id.img_search);
        imvCloseSearch = view.findViewById(R.id.imvCloseSearch);
        layoutSearch = view.findViewById(R.id.layoutSearch);
        listResultSearch = view.findViewById(R.id.listResultSearch);
        layoutSong = view.findViewById(R.id.layoutSong);
    }
}