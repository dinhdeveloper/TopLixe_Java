package com.java.music.adapter.film;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.java.music.R;
import com.java.music.adapter.song.SongSearchAdapter;
import com.java.music.model.film.FilmEntity;
import com.java.music.model.film.FilmEntityModel;
import com.java.music.model.song.SongEntityModel;

import java.util.List;

public class FilmSearchAdapter extends RecyclerView.Adapter<FilmSearchAdapter.ViewHolder> {

    FilmSearchAdapterListenner listenner;

    public interface FilmSearchAdapterListenner{
        void setOnClickItem(FilmEntityModel entity);
    }

    public void setListenner(FilmSearchAdapterListenner listenner) {
        this.listenner = listenner;
    }

    List<FilmEntityModel> list;
    Context context;

    public FilmSearchAdapter(List<FilmEntityModel> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_layout_search_film,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FilmEntityModel songEntity = list.get(position);
        try{
            if (songEntity!=null){
                Glide.with(context).load(songEntity.getFilmEntity().getImg()).error(R.drawable.imageloading).into(holder.image_song);
                holder.song_name.setText(songEntity.getFilmEntity().getFilmname());
                holder.singer_name.setText(songEntity.getActorEntityList().get(0).getActorname());
            }
        }catch (Exception e){
            Log.e("Ex",e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image_song,imgPlay;
        LinearLayout layout_song;
        TextView song_name,singer_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPlay = itemView.findViewById(R.id.imgPlay);
            image_song = itemView.findViewById(R.id.image_song);
            singer_name = itemView.findViewById(R.id.singer_name);
            song_name = itemView.findViewById(R.id.song_name);
            layout_song = itemView.findViewById(R.id.layout_song);

            layout_song.setOnClickListener(v -> {
                if (listenner !=null){
                    listenner.setOnClickItem(list.get(getAdapterPosition()));
                }
            });
        }
    }
}