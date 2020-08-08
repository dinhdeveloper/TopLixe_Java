package com.java.music.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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

        imvBackAblum = findViewById(R.id.imvBackAblum);
        albumName = findViewById(R.id.albumName);
        rc_load_music_more = findViewById(R.id.rc_load_music_more);

        Intent intent = getIntent();
        AlbumEntityModel entityModel = (AlbumEntityModel)intent.getExtras().get("album");

        if (entityModel!=null){
            albumName.setText(entityModel.getAlbumEntity().getAlbumname());
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

                    String customURL = Const.HOST_MUSIC+ model.getUploadsource();

                    if (isValid(customURL)) {
                        Intent intents = new Intent(getApplicationContext(), SongDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("model", model);
                        intents.putExtras(bundle);
                        startActivity(intents);
                    } else {
                        Toast.makeText(getApplicationContext(), "Link nhạc không tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}