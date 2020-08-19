package com.java.music.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.java.music.R;
import com.java.music.api.APIService;
import com.java.music.api.APIUntil;
import com.java.music.model.Token;
import com.java.music.model.song.SongEntity;
import com.java.music.model.song.SongEntityModel;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.java.music.activity.MainActivity.MEDIAPLAYER;
import static com.java.music.fragment.song.SongFragment.isValid;

public class SongDetailActivity extends AppCompatActivity {

    APIService apiService;

    CircleImageView imgDiaNhac;
    ProgressBar progress;
    SeekBar seekBarSong;
    ImageView imvBack;
    TextView txtTimeSong, txtTimeTotalSong;
    ImageButton imgShuffle, imgPrevious, imgPlay, imgNext, imgReplay;

    SongEntity entityModel;
    int id_song;

    Token token = new Token();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_detail);

//        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
//            View v = this.getWindow().getDecorView();
//            v.setSystemUiVisibility(View.GONE);
//        } else if (Build.VERSION.SDK_INT >= 19) {
//            //for new api versions.
//            View decorView = getWindow().getDecorView();
//            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
//            decorView.setSystemUiVisibility(uiOptions);
//        }

        apiService = APIUntil.getServer();

        Intent intent = getIntent();
        id_song = (int) intent.getExtras().get("model");

        addControl();
        getData(id_song);

        onClick();
    }

    private void onClick() {
        //back
        imvBack.setOnClickListener(v -> {
            onBackPressed();
        });
        //xao tron bai hat
        imgShuffle.setOnClickListener(v -> {

            LayoutInflater layoutInflater = getLayoutInflater();
            View popupView = layoutInflater.inflate(R.layout.loading, null);
            AlertDialog.Builder alert = new AlertDialog.Builder(SongDetailActivity.this);
            alert.setView(popupView);
            AlertDialog dialog = alert.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (MEDIAPLAYER != null) {
                        MEDIAPLAYER.stop();
                        MEDIAPLAYER.reset();
                        MEDIAPLAYER.release();
                        MEDIAPLAYER = null;
                    }
                    MEDIAPLAYER = null;
                    Random rd = new Random();
                    id_song = rd.nextInt(50);
                    getDataShuffle(id_song);
                    dialog.dismiss();
                }
            }, 1000);
        });

        //lap lai bai hat
        imgReplay.setOnClickListener(v -> {

        });
        //lui lai 1 bai
        imgPrevious.setOnClickListener(v -> {
//            SweetAlertDialog pDialog = new SweetAlertDialog(SongDetailActivity.this, SweetAlertDialog.PROGRESS_TYPE);
//            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//            pDialog.setTitleText("Loading ...");
//            pDialog.setCancelable(false);
//            pDialog.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (MEDIAPLAYER != null) {
                        MEDIAPLAYER.stop();
                        MEDIAPLAYER.reset();
                        MEDIAPLAYER.release();
                        MEDIAPLAYER = null;
                    }
                    MEDIAPLAYER = null;
                    id_song = id_song - 1;
                    getDataPrevious(id_song);

                    //pDialog.dismissWithAnimation();
                }
            }, 1000);
        });

        //next
        imgNext.setOnClickListener(v -> {
//            SweetAlertDialog pDialog = new SweetAlertDialog(SongDetailActivity.this, SweetAlertDialog.PROGRESS_TYPE);
//            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//            pDialog.setTitleText("Loading ...");
//            pDialog.setCancelable(false);
//            pDialog.show();

//            LayoutInflater layoutInflater = getLayoutInflater();
//            View popupView = layoutInflater.inflate(R.layout.loading, null);
//            AlertDialog.Builder alert = new AlertDialog.Builder(SongDetailActivity.this);
//            alert.setView(popupView);
//            AlertDialog dialog = alert.create();
//            //dialog.setCanceledOnTouchOutside(false);
//            dialog.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (MEDIAPLAYER != null) {
                        MEDIAPLAYER.stop();
                        MEDIAPLAYER.reset();
                        MEDIAPLAYER.release();
                        MEDIAPLAYER = null;
                    }
                    MEDIAPLAYER = null;
                    id_song = id_song + 1;
                    getDataNext(id_song);
                    //dialog.dismiss();
                   // pDialog.dismissWithAnimation();
                }
            }, 1000);
        });
    }

    private void getDataNext(int id) {
        token.setApiToken("7E277A25310E4D1AA3E6B0F0615AD39A");
        apiService.getSongDetail(token,id).enqueue(new Callback<SongEntityModel>() {
            @Override
            public void onResponse(Call<SongEntityModel> call, Response<SongEntityModel> response) {

                if (response.body() != null) {
                    entityModel = response.body().getSongEntity();
                    if (isValid(entityModel.getUploadsource())) {
                       // Toast.makeText(SongDetailActivity.this, ""+id_song, Toast.LENGTH_SHORT).show();
                        playNhac(entityModel);
                    } else {
                        Toast.makeText(SongDetailActivity.this, "Không tìm thấy link bài hát", Toast.LENGTH_SHORT).show();
                        id_song += 1;
                        if (MEDIAPLAYER!=null){
                            MEDIAPLAYER.stop();
                            imgPlay.setImageResource(R.drawable.ic_play);
                        }
                        getDataNext(id_song);
                    }
                } else {
                    id_song += 1;
                    getDataNext(id_song);
                }
            }

            @Override
            public void onFailure(Call<SongEntityModel> call, Throwable t) {
                Log.e("eeee", t.getMessage());
            }
        });


    }

    private void getDataPrevious(int id) {
        token.setApiToken("7E277A25310E4D1AA3E6B0F0615AD39A");
        apiService.getSongDetail(token,id).enqueue(new Callback<SongEntityModel>() {
            @Override
            public void onResponse(Call<SongEntityModel> call, Response<SongEntityModel> response) {

                if (response.body() != null) {
                    entityModel = response.body().getSongEntity();
                    if (isValid(entityModel.getUploadsource())) {
                        //Toast.makeText(SongDetailActivity.this, ""+id_song, Toast.LENGTH_SHORT).show();
                        playNhac(entityModel);
                    } else {
                        Toast.makeText(SongDetailActivity.this, "Không tìm thấy link bài hát", Toast.LENGTH_SHORT).show();
                        id_song -= 1;
                        if (MEDIAPLAYER!=null){
                            MEDIAPLAYER.stop();
                            imgPlay.setImageResource(R.drawable.ic_play);
                        }
                        getDataPrevious(id_song);
                    }
                } else {
                    id_song -= 1;
                    getDataPrevious(id_song);
                }
            }

            @Override
            public void onFailure(Call<SongEntityModel> call, Throwable t) {
                Log.e("eeee", t.getMessage());
            }
        });


    }

    private void getData(int id) {
        token.setApiToken("7E277A25310E4D1AA3E6B0F0615AD39A");
        apiService.getSongDetail(token,id).enqueue(new Callback<SongEntityModel>() {
            @Override
            public void onResponse(Call<SongEntityModel> call, Response<SongEntityModel> response) {
                if (response.body() != null) {
                    //Toast.makeText(SongDetailActivity.this, ""+id_song, Toast.LENGTH_SHORT).show();
                    playNhac(response.body().getSongEntity());
                }
            }

            @Override
            public void onFailure(Call<SongEntityModel> call, Throwable t) {
                Log.e("eeee", t.getMessage());
            }
        });

    }

    private void getDataShuffle(int id) {
        token.setApiToken("7E277A25310E4D1AA3E6B0F0615AD39A");
        apiService.getSongDetail(token,id).enqueue(new Callback<SongEntityModel>() {
            @Override
            public void onResponse(Call<SongEntityModel> call, Response<SongEntityModel> response) {

                if (response.body() != null) {
                    entityModel = response.body().getSongEntity();
                    if (isValid(entityModel.getUploadsource())) {
                        playNhac(entityModel);
                    } else {
                        Toast.makeText(SongDetailActivity.this, "Không tìm thấy link bài hát", Toast.LENGTH_SHORT).show();
                        id_song += 1;
                        getData(id_song);
                    }
                } else {
                    id_song += 1;
                    getData(id_song);
                }
            }

            @Override
            public void onFailure(Call<SongEntityModel> call, Throwable t) {
                Log.e("eeee", t.getMessage());
            }
        });


    }


    private void playNhac(SongEntity entityModel) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imgDiaNhac, "rotation", 0f, 360f);
        animator.setDuration(10000);// thoi gian xoay dia nhac
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();

        Glide.with(getApplicationContext()).load(entityModel.getImg()).into(imgDiaNhac);

        MEDIAPLAYER = new MediaPlayer();
        MEDIAPLAYER.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            MEDIAPLAYER.setDataSource(entityModel.getUploadsource());
            MEDIAPLAYER.prepare();
            MEDIAPLAYER.start();
            MEDIAPLAYER.seekTo(0);
            imgPlay.setImageResource(R.drawable.ic_pause);
        } catch (IllegalArgumentException e) {

            //Toast.makeText(getApplicationContext(), "Link nhạc không tồn tại", Toast.LENGTH_LONG).show();

        } catch (SecurityException e) {

            //Toast.makeText(getApplicationContext(), "Link nhạc không tồn tại", Toast.LENGTH_LONG).show();

        } catch (IllegalStateException e) {

            //Toast.makeText(getApplicationContext(), "Link nhạc không tồn tại", Toast.LENGTH_LONG).show();

        } catch (IOException e) {

            e.printStackTrace();

        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        txtTimeTotalSong.setText(simpleDateFormat.format(MEDIAPLAYER.getDuration()) + "");
        seekBarSong.setMax(MEDIAPLAYER.getDuration());

        imgPlay.setOnClickListener(v -> {
            if (MEDIAPLAYER != null) {
                if (MEDIAPLAYER.isPlaying()) {
                    MEDIAPLAYER.pause();
                    imgPlay.setImageResource(R.drawable.ic_play);
                } else {
                    MEDIAPLAYER.start();
                    animator.start();
                    imgPlay.setImageResource(R.drawable.ic_pause);
                }
            }
        });
        updateTime();

        seekBarSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (MEDIAPLAYER!=null){
                    MEDIAPLAYER.seekTo(seekBar.getProgress());
                }else {
                }
            }
        });

    }

    private void addControl() {
        imgDiaNhac = findViewById(R.id.imgDiaNhac);
        seekBarSong = findViewById(R.id.seekBarSong);
        txtTimeTotalSong = findViewById(R.id.txtTimeTotalSong);
        txtTimeSong = findViewById(R.id.txtTimeSong);
        imgShuffle = findViewById(R.id.imgShuffle);
        imgPlay = findViewById(R.id.imgPlay);
        imgNext = findViewById(R.id.imgNext);
        imgPrevious = findViewById(R.id.imgPrevious);
        imgReplay = findViewById(R.id.imgReplay);
        progress = findViewById(R.id.progress);
        imvBack = findViewById(R.id.imvBack);
    }

    private void updateTime() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (MEDIAPLAYER != null) {
                    seekBarSong.setProgress(MEDIAPLAYER.getCurrentPosition());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                    txtTimeSong.setText("" + simpleDateFormat.format(MEDIAPLAYER.getCurrentPosition()));
                    handler.postDelayed(this, 300);
                    MEDIAPLAYER.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            try {
                                Thread.sleep(1000);
                                imgPlay.setImageResource(R.drawable.ic_play);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }, 500);
    }
}