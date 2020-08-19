package com.java.music.adapter.song;

import android.content.Context;
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
import com.java.music.model.song.SongEntityModel;

import java.util.List;

public class SongHomeAdapter extends RecyclerView.Adapter<SongHomeAdapter.ViewHolder> {

    SongHomeAdapterListener listener;


    public  interface SongHomeAdapterListener{
        void onClickItem(SongEntityModel model);
    }

    public void setListener(SongHomeAdapterListener listener) {
        this.listener = listener;
    }

    Context context;
    List<SongEntityModel> list;

    public SongHomeAdapter(Context context, List<SongEntityModel> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_item_song_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SongEntityModel entityModel = list.get(position);
        if (entityModel!=null){
            holder.nameSong.setText(entityModel.getSongEntity().getSongname());
            Glide.with(context).load(entityModel.getSongEntity().getImg()).error(R.drawable.imageloading).into(holder.imageSong);
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
        TextView nameSong;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layoutClick = itemView.findViewById(R.id.layoutClick);
            imageSong = itemView.findViewById(R.id.imageSong);
            nameSong = itemView.findViewById(R.id.nameSong);

            layoutClick.setOnClickListener(v -> {
                if (listener!=null){
                    listener.onClickItem(list.get(getAdapterPosition()));
                }
            });
        }
    }
}
