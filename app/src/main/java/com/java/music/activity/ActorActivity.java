package com.java.music.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.java.music.R;
import com.java.music.adapter.film.FilmOfActorAdapter;
import com.java.music.common.Const;
import com.java.music.model.actor.ActorEntityModel;

import static com.java.music.activity.MainActivity.MEDIAPLAYER;
import static com.java.music.fragment.home.HomeFragment.isValid;

public class ActorActivity extends AppCompatActivity {

    ImageView imageActor, imvBackActor;
    RecyclerView rc_film_actor;
    TextView actorName, actorSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor);

        imageActor = findViewById(R.id.imageActor);
        imvBackActor = findViewById(R.id.imvBackActor);
        rc_film_actor = findViewById(R.id.rc_film_actor);
        actorName = findViewById(R.id.actorName);
        actorSize = findViewById(R.id.actorSize);

        Intent intent = getIntent();
        ActorEntityModel entityModel = (ActorEntityModel) intent.getExtras().get("model");

        if (entityModel != null) {
            Glide.with(getApplicationContext()).load(entityModel.getImageEntity().getPath()).into(imageActor);
            actorName.setText(entityModel.getActorEntity().getActorname());
            rc_film_actor.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
            FilmOfActorAdapter adapter = new FilmOfActorAdapter(getApplicationContext(), entityModel.getFilmDTOList());
            rc_film_actor.setAdapter(adapter);

            adapter.setListener(model -> {
                if (model != null) {
                    if (MEDIAPLAYER != null) {
                        MEDIAPLAYER.stop();
                        MEDIAPLAYER.reset();
                        MEDIAPLAYER.release();
                    }
                    MEDIAPLAYER = null;
                    String customURL = Const.HOST_MUSIC+ model.getFilmEntity().getUploadsource();

                    if (isValid(customURL)) {
                        Intent intents = new Intent(getApplicationContext(), FilmDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("model",model);
                        intents.putExtras(bundle);
                        startActivity(intents);
                    } else {
                        Toast.makeText(getApplicationContext(), "Link phim không tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}