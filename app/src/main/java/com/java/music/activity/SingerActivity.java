package com.java.music.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.java.music.R;
import com.java.music.adapter.singer.LoadSongOfSingerAdapter;
import com.java.music.common.Const;
import com.java.music.model.singer.SingerEntityModel;

import static com.java.music.activity.MainActivity.MEDIAPLAYER;
import static com.java.music.fragment.home.HomeFragment.isValid;

public class SingerActivity extends AppCompatActivity {

    ImageView imageActor,imvBackSinger;
    RecyclerView rc_singer;
    TextView actorName, actorSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singer);

        addControls();

        Intent intent = getIntent();
        SingerEntityModel entityModel = (SingerEntityModel) intent.getSerializableExtra("singer");
        if (entityModel != null) {
            Glide.with(getApplicationContext()).load(entityModel.getSingerEntity().getImg()).into(imageActor);
            actorName.setText(entityModel.getSingerEntity().getSingername());
            actorSize.setText("Ca sĩ: " + entityModel.getSingerEntity().getSingername() + " có " + entityModel.getSongDTOList().size() + " bài hát.");

            rc_singer.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
            LoadSongOfSingerAdapter singerAdapter = new LoadSongOfSingerAdapter(getApplicationContext(), entityModel.getSongDTOList());
            rc_singer.setAdapter(singerAdapter);
            singerAdapter.notifyDataSetChanged();

            singerAdapter.setListener(model -> {
                if (model != null) {

                    if (MEDIAPLAYER != null) {
                        MEDIAPLAYER.stop();
                        MEDIAPLAYER.reset();
                        MEDIAPLAYER.release();
                    }
                    MEDIAPLAYER = null;
                    String customURL = Const.HOST_MUSIC+ model.getSongEntity().getUploadsource();

                    if (isValid(customURL)) {
                        Intent intents = new Intent(getApplicationContext(), SongDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("model", model.getSongEntity());
                        intents.putExtras(bundle);
                        startActivity(intents);
                    } else {
                        Toast.makeText(getApplicationContext(), "Link nhạc không tồn tại", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

        imvBackSinger.setOnClickListener(v -> {
            finish();
        });
    }

    private void addControls() {
        imageActor = findViewById(R.id.imageActor);
        rc_singer = findViewById(R.id.rc_singer);
        actorName = findViewById(R.id.actorName);
        actorSize = findViewById(R.id.actorSize);
        imvBackSinger = findViewById(R.id.imvBackSinger);
    }
}