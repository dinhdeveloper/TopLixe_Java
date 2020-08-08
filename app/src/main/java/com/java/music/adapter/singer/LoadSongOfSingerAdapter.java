package com.java.music.adapter.singer;

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
import com.java.music.model.song.SongEntity;
import com.java.music.model.song.SongEntityModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoadSongOfSingerAdapter extends RecyclerView.Adapter<LoadSongOfSingerAdapter.ViewHolder> {

    LoadSongOfSingerAdapterListener listener;


    public interface LoadSongOfSingerAdapterListener {
        void onClickItem(SongEntityModel model);
    }

    public void setListener(LoadSongOfSingerAdapterListener listener) {
        this.listener = listener;
    }

    Context context;
    List<SongEntityModel> list;

    public LoadSongOfSingerAdapter(Context context, List<SongEntityModel> list) {
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
        SongEntityModel entityModel = list.get(position);
        try {
            if (entityModel != null) {
                holder.txtAd.setText(entityModel.getSongEntity().getSongname());
                Glide.with(context).load(entityModel.getSongEntity().getImg()).into(holder.imgItem);
            }
        } catch (Exception e) {
            Log.e("Exx", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
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