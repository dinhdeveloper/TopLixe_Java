package com.java.music.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.java.music.R;
import com.java.music.fragment.film.FilmFragment;
import com.java.music.fragment.home.HomeFragment;
import com.java.music.fragment.song.SongFragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    public static MediaPlayer MEDIAPLAYER = null;
    boolean doubleBackToExitPressedOnce = false;

    public static boolean CHECKSONG = false;

    LinearLayout layout_song;
    ImageView imvPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //FullScreencall();
        isOnline();
        layout_song = findViewById(R.id.layout_song);
        imvPlay = findViewById(R.id.imvPlay);
        BottomNavigationView navBottom = findViewById(R.id.navBottom);
        navBottom.setOnNavigationItemSelectedListener(this);
        defauFragment(new HomeFragment());
    }
    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (MEDIAPLAYER!=null){
            layout_song.setVisibility(View.VISIBLE);
            if (CHECKSONG==true){
                imvPlay.setImageResource(R.drawable.ic_pause);
                imvPlay.setOnClickListener(v -> {
                    if (MEDIAPLAYER.isPlaying()) {
                        MEDIAPLAYER.pause();
                        imvPlay.setImageResource(R.drawable.ic_play);
                    } else {
                        MEDIAPLAYER.start();
                        imvPlay.setImageResource(R.drawable.ic_pause);
                    }
                });
            }else {
                layout_song.setVisibility(View.GONE);
            }
        }else {
            layout_song.setVisibility(View.GONE);
        }
    }

    private void isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null && ni.isConnected()) {
            Thread timer = new Thread() {
                public void run() {
                    try {
                        sleep(4000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
//                    } finally {
//                        Intent i = new Intent(SplashActivity.this, MainActivity.class);
//                        finish();
//                        startActivity(i);
//                    }
                }
            };
            timer.start();
        } else {
            Toast.makeText(this, "Bạn chưa kết nối mạng", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void FullScreencall() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.item_home:
                fragment = new HomeFragment();
                break;
            case R.id.item_music:
                fragment = new SongFragment();
                break;
            case R.id.item_film:
                fragment = new FilmFragment();
                break;

        }
        return defauFragment(fragment);
    }
    private boolean defauFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.layoutMain, fragment)
                    .commit();
            return true;
        }
        return false;
    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Nhấn lần nữa để thoát", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}