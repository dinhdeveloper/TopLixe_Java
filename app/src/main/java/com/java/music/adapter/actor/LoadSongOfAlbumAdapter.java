package com.java.music.adapter.actor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.java.music.R;
import com.java.music.model.song.SongEntity;

import java.util.List;

public class LoadSongOfAlbumAdapter extends RecyclerView.Adapter<LoadSongOfAlbumAdapter.ViewHolder> {

    LoadSongOfAlbumAdapterListener listener;


    public interface LoadSongOfAlbumAdapterListener {
        void onClickItem(SongEntity model);
    }

    public void setListener(LoadSongOfAlbumAdapterListener listener) {
        this.listener = listener;
    }

    Context context;
    List<SongEntity> list;

    public LoadSongOfAlbumAdapter(Context context, List<SongEntity> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_item_singer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SongEntity entityModel = list.get(position);
        try {
            if (entityModel != null) {
                holder.txtAd.setText(entityModel.getSongname());
                Glide.with(context).load(entityModel.getImg()).error(R.drawable.imageloading).into(holder.imgItem);
            }
        } catch (Exception e) {
            Log.e("Exx", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        if (list!=null){
            return list.size();
        }else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout layoutClick;
        ImageView imgItem;
        TextView txtAd,txt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgItem = itemView.findViewById(R.id.imgItem);
            txtAd = itemView.findViewById(R.id.txtAd);
            txt = itemView.findViewById(R.id.txt);
            layoutClick = itemView.findViewById(R.id.layoutClick);

            layoutClick.setOnClickListener(v -> {
                if (listener!=null)
                    listener.onClickItem(list.get(getAdapterPosition()));
            });

        }
    }
}