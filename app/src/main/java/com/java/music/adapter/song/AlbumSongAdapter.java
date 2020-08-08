package com.java.music.adapter.song;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.java.music.R;
import com.java.music.model.song.AlbumEntityModel;

import java.util.List;

public class AlbumSongAdapter extends RecyclerView.Adapter<AlbumSongAdapter.ViewHolder> {

    AlbumSongAdapterListener listener;


    public  interface AlbumSongAdapterListener{
        void onClickItem(AlbumEntityModel model);
    }

    public void setListener(AlbumSongAdapterListener listener) {
        this.listener = listener;
    }

    Context context;
    List<AlbumEntityModel> list;

    public AlbumSongAdapter(Context context, List<AlbumEntityModel> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_item_music, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AlbumEntityModel entityModel = list.get(position);
        try{
            if (entityModel!=null){
                holder.nameSong.setText(entityModel.getAlbumEntity().getAlbumname());
               // holder.nameSong.setText(entityModel.getSingerEntity());
                if (entityModel.getSongEntity()!=null){
                    Glide.with(context).load(entityModel.getSongEntity().get(0).getImg()).into(holder.imageSong);
                }else {
                    Glide.with(context).load(R.drawable.bontram).into(holder.imageSong);
                }
            }
        }catch (Exception e){
            Log.e("Exe",e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        if (list.size()>9){
            return 9;
        }
        else {
            return list.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageSong;
        LinearLayout layoutClick;
        TextView nameSong,singerName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layoutClick = itemView.findViewById(R.id.layoutClick);
            imageSong = itemView.findViewById(R.id.imageSong);
            nameSong = itemView.findViewById(R.id.nameSong);
            singerName = itemView.findViewById(R.id.singerName);

            layoutClick.setOnClickListener(v -> {
                if (listener!=null){
                    listener.onClickItem(list.get(getAdapterPosition()));
                }
            });
        }
    }
}
