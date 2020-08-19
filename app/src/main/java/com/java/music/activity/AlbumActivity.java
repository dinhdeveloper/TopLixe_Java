package com.java.music.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.java.music.R;
import com.java.music.adapter.actor.LoadSongOfAlbumAdapter;
import com.java.music.adapter.singer.LoadSongOfSingerAdapter;
import com.java.music.common.Const;
import com.java.music.model.song.AlbumEntityModel;

import static com.java.music.activity.MainActivity.MEDIAPLAYER;
import static com.java.music.fragment.home.HomeFragment.isValid;

public class AlbumActivity extends AppCompatActivity {


    ImageView imvBackAblum;
    TextView albumName;
    RecyclerView rc_load_music_more;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_detail);

//        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
//            View v = this.getWindow().getDecorView();
//            v.setSystemUiVisibility(View.GONE);
//        } else if (Build.VERSION.SDK_INT >= 19) {
//            //for new api versions.
//            View decorView = getWindow().getDecorView();
//            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
//            decorView.setSystemUiVisibility(uiOptions);
//        }

        imvBackAblum = findViewById(R.id.imvBackAblum);
        albumName = findViewById(R.id.albumName);
        rc_load_music_more = findViewById(R.id.rc_load_music_more);

        imvBackAblum.setOnClickListener(v -> {
            onBackPressed();
        });

        Intent intent = getIntent();
        AlbumEntityModel entityModel = (AlbumEntityModel)intent.getExtras().get("album");

        if (entityModel!=null){
            albumName.setText(entityModel.getAlbumEntity().getAlbumname());
            if (entityModel.getSongEntity() !=null){
                rc_load_music_more.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
                LoadSongOfAlbumAdapter adapter = new LoadSongOfAlbumAdapter(getApplicationContext(),entityModel.getSongEntity());
                rc_load_music_more.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                adapter.setListener(model -> {
                    if (model != null) {

                        if (MEDIAPLAYER != null) {
                            MEDIAPLAYER.stop();
                            MEDIAPLAYER.reset();
                            MEDIAPLAYER.release();
                        }
                        MEDIAPLAYER = null;

                        String customURL = model.getUploadsource();

                        if (isValid(customURL)) {
                            Intent intents = new Intent(getApplicationContext(), SongDetailActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("model", model.getId());
                            intents.putExtras(bundle);
                            startActivity(intents);
                        } else {
                            Toast.makeText(getApplicationContext(), "Link nhạc không tồn tại", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }else {
                Toast.makeText(this, "Không có bài hát trong album này.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}