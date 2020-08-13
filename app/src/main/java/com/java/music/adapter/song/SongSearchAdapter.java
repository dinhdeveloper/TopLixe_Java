package com.java.music.adapter.song;

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
import com.java.music.model.song.SongEntity;
import com.java.music.model.song.SongEntityModel;

import java.util.List;

public class SongSearchAdapter extends RecyclerView.Adapter<SongSearchAdapter.ViewHolder> {

    SongSearchAdapterListenner listenner;

    public interface SongSearchAdapterListenner{
        void setOnClickItem(SongEntityModel entity);
    }

    public void setListenner(SongSearchAdapterListenner listenner) {
        this.listenner = listenner;
    }

    List<SongEntityModel> list;
    Context context;

    public SongSearchAdapter(List<SongEntityModel> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_layout_search_song,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SongEntityModel songEntity = list.get(position);
        try{
            if (songEntity!=null){
                Glide.with(context).load(songEntity.getSongEntity().getImg()).error(R.drawable.bontram).into(holder.image_song);
                holder.song_name.setText(songEntity.getSongEntity().getSongname());
                holder.singer_name.setText(songEntity.getSingerEntityList().get(0).getSingername());
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
