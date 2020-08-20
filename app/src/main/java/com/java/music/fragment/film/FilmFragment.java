package com.java.music.fragment.film;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.java.music.R;
import com.java.music.activity.ActorActivity;
import com.java.music.activity.FilmDetailActivity;
import com.java.music.activity.LoginActivity;
import com.java.music.activity.MainActivity;
import com.java.music.adapter.actor.FilmActorAdapter;
import com.java.music.adapter.film.FilmHasPageAdapter;
import com.java.music.adapter.film.FilmSearchAdapter;
import com.java.music.adapter.film.FilmSuggrestionHomeAdapter;
import com.java.music.adapter.song.SongSearchAdapter;
import com.java.music.api.APIService;
import com.java.music.api.APIUntil;
import com.java.music.common.SharePrefs;
import com.java.music.model.CustomerModel;
import com.java.music.model.Token;
import com.java.music.model.actor.ActorEntity;
import com.java.music.model.actor.ActorEntityModel;
import com.java.music.model.film.FilmEntity;
import com.java.music.model.film.FilmEntityModel;
import com.java.music.model.song.SongEntityModel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.java.music.activity.MainActivity.MEDIAPLAYER;
import static com.java.music.fragment.home.HomeFragment.isValid;

public class FilmFragment extends Fragment {

    APIService apiService;
    NestedScrollView layoutFilm;
    RecyclerView listResultSearch;
    ImageView img_search, imvCloseSearch;
    LinearLayout layoutSearch;
    EditText edtSearch;
    TextView txtNameUser;
    CircleImageView imgUser;

    CardView card_hot, card_recommend, card_actor;
    RecyclerView rc_listHot, rc_listSuggest, rc_actor, rc_listFilm;

    Token token = new Token();
    CustomerModel customerModel;
    SharePrefs prefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_film, container, false);

        apiService = APIUntil.getServer();
        addControls(view);
        prefs = new SharePrefs(getActivity());
        customerModel = prefs.getUserModel();
        if (customerModel != null) {
            txtNameUser.setText(customerModel.getUserEntity().getDisplayname());
        }
        callFilmHot();
        callGoiYChoBan();
        callActor();
        callFilmHasPage();


        onClick();

        return view;
    }

    private void onClick() {
        img_search.setOnClickListener(v -> {
            layoutSearch.setVisibility(View.VISIBLE);
            img_search.setVisibility(View.GONE);
            edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                    if (edtSearch.getText().toString() != null && !edtSearch.getText().toString().isEmpty()) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            searchFilm(edtSearch.getText().toString());
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
                edtSearch.setText(null);
                listResultSearch.setVisibility(View.GONE);
                layoutFilm.setVisibility(View.VISIBLE);
            });
        });
        imgUser.setOnClickListener(v -> {
//            LayoutInflater layoutInflater = getLayoutInflater();
//            View popupView = layoutInflater.inflate(R.layout.custom_popup_logout, null);
//            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
//            alert.setView(popupView);
//            AlertDialog dialog = alert.create();
//            //dialog.setCanceledOnTouchOutside(false);
//            dialog.show();

            new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Đăng xuất")
                    .setContentText("Bạn có muốn đăng xuất khỏi thiết bị không?")
                    .setConfirmText("Đồng ý")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            prefs.clear();
                            startActivity(new Intent(getContext(), LoginActivity.class));
                            getActivity().finish();
                        }
                    })
                    .setCancelButton("Quay lại", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .show();
        });
    }

    private void searchFilm(String search) {
        if (search != null) {
            token.setApiToken("7E277A25310E4D1AA3E6B0F0615AD39A");
            apiService.getSearchFilm(token, search).enqueue(new Callback<List<FilmEntityModel>>() {
                @Override
                public void onResponse(Call<List<FilmEntityModel>> call, Response<List<FilmEntityModel>> response) {
                    if (response.isSuccessful()) {
                        if (!response.body().isEmpty()) {
                            listResultSearch.setVisibility(View.VISIBLE);
                            layoutFilm.setVisibility(View.GONE);
                            FilmSearchAdapter adapter = new FilmSearchAdapter(response.body(), getContext());
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
                                    String customURL = entity.getFilmEntity().getUploadsource();

                                    if (isValid(customURL)) {
                                        Intent intent = new Intent(getContext(), FilmDetailActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("model", entity);
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(getContext(), "Link phim không tồn tại", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(getContext(), "Không tìm thấy phim", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<FilmEntityModel>> call, Throwable t) {
                    Log.e("onFailure", t.getMessage());
                }
            });
        }
    }

    private void callFilmHasPage() {
        token.setApiToken("7E277A25310E4D1AA3E6B0F0615AD39A");
        apiService.getHasPageFilm(token, 10, 0).enqueue(new Callback<List<FilmEntityModel>>() {
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

    private void callActor() {
        token.setApiToken("7E277A25310E4D1AA3E6B0F0615AD39A");
        apiService.getActor(token, 10, 0).enqueue(new Callback<List<ActorEntityModel>>() {
            @Override
            public void onResponse(Call<List<ActorEntityModel>> call, Response<List<ActorEntityModel>> response) {
                if (response.isSuccessful()) {
                    card_actor.setVisibility(View.VISIBLE);
                    rc_actor.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                    FilmActorAdapter songRandomHomeAdapter = new FilmActorAdapter(getContext(), response.body());
                    rc_actor.setAdapter(songRandomHomeAdapter);
                    songRandomHomeAdapter.notifyDataSetChanged();
                    songRandomHomeAdapter.setListener(model -> {
                        if (!model.getFilmDTOList().isEmpty()) {
                            Intent intent = new Intent(getContext(), ActorActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("model", model);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getContext(), "Không có phim cho diễn viên này.", Toast.LENGTH_SHORT).show();
                        }
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
        token.setApiToken("7E277A25310E4D1AA3E6B0F0615AD39A");
        apiService.getAllFilm(token).enqueue(new Callback<List<FilmEntityModel>>() {
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

    private void callFilmHot() {
        token.setApiToken("7E277A25310E4D1AA3E6B0F0615AD39A");
        apiService.getAllFilm(token).enqueue(new Callback<List<FilmEntityModel>>() {
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

    private void addControls(View view) {
        card_hot = view.findViewById(R.id.card_hot);
        card_recommend = view.findViewById(R.id.card_recommend);
        card_actor = view.findViewById(R.id.card_actor);
        rc_listHot = view.findViewById(R.id.rc_listHot);
        rc_listSuggest = view.findViewById(R.id.rc_listSuggest);
        rc_actor = view.findViewById(R.id.rc_actor);
        rc_listFilm = view.findViewById(R.id.rc_listFilm);
        layoutFilm = view.findViewById(R.id.layoutFilm);
        listResultSearch = view.findViewById(R.id.listResultSearch);
        img_search = view.findViewById(R.id.img_search);
        imvCloseSearch = view.findViewById(R.id.imvCloseSearch);
        layoutSearch = view.findViewById(R.id.layoutSearch);
        edtSearch = view.findViewById(R.id.edtSearch);
        txtNameUser = view.findViewById(R.id.txtNameUser);
        imgUser = view.findViewById(R.id.imgUser);
    }
}