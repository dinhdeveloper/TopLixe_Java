package com.java.music.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.java.music.R;
import com.java.music.api.APIService;
import com.java.music.api.APIUntil;
import com.java.music.common.Const;
import com.java.music.model.film.FilmEntityModel;
import com.java.music.model.song.AlbumEntityModel;
import com.java.music.model.song.SongEntity;
import com.java.music.model.song.SongEntityModel;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.java.music.activity.MainActivity.MEDIAPLAYER;

public class SongDetailActivity extends AppCompatActivity {

    APIService apiService;

    CircleImageView imgDiaNhac;
    SeekBar seekBarSong;
    TextView txtTimeSong, txtTimeTotalSong;
    ImageButton imgShuffle, imgPrevious, imgPlay, imgNext, imgReplay;

    SongEntity entityModel;
    int id_song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_detail);

        apiService = APIUntil.getServer();

        Intent intent = getIntent();
        id_song = (int) intent.getExtras().get("model");

        addControl();
        getData(id_song);

        onClick();
    }

    private void onClick() {
        //xao tron bai hat
        imgShuffle.setOnClickListener(v -> {

        });

        //lap lai bai hat
        imgReplay.setOnClickListener(v -> {

        });
        //lui lai 1 bai
        imgPrevious.setOnClickListener(v -> {
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
                    getData(id_song);
                }
            },100);
        });

        //next
        imgNext.setOnClickListener(v -> {
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
                    getData(id_song);
                }
            },100);
        });
    }

    private void getData(int id) {

        apiService.getSongDetail(id).enqueue(new Callback<SongEntityModel>() {
            @Override
            public void onResponse(Call<SongEntityModel> call, Response<SongEntityModel> response) {
                Log.e("AAAAA",response.body().getSongEntity().getId()+"  "+response.body().getSongEntity().getUploadsource());
                entityModel = response.body().getSongEntity();
                playNhac(entityModel);
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
            MEDIAPLAYER.setDataSource(Const.HOST_MUSIC + entityModel.getUploadsource());
            MEDIAPLAYER.prepare();
            MEDIAPLAYER.start();
            MEDIAPLAYER.seekTo(1);
            imgPlay.setImageResource(R.drawable.ic_pause);
        } catch (IllegalArgumentException e) {

            Toast.makeText(getApplicationContext(), "Link nhạc không tồn tại", Toast.LENGTH_LONG).show();

        } catch (SecurityException e) {

            Toast.makeText(getApplicationContext(), "Link nhạc không tồn tại", Toast.LENGTH_LONG).show();

        } catch (IllegalStateException e) {

            Toast.makeText(getApplicationContext(), "Link nhạc không tồn tại", Toast.LENGTH_LONG).show();

        } catch (IOException e) {

            e.printStackTrace();

        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        txtTimeTotalSong.setText(simpleDateFormat.format(MEDIAPLAYER.getDuration()) + "");
        seekBarSong.setMax(MEDIAPLAYER.getDuration());

        imgPlay.setOnClickListener(v -> {
            if (MEDIAPLAYER!=null){
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
                MEDIAPLAYER.seekTo(seekBar.getProgress());
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