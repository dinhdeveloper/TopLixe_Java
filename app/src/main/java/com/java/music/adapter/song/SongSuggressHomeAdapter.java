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
import com.java.music.model.song.SongEntityModel;

import java.util.List;

public class SongSuggressHomeAdapter extends RecyclerView.Adapter<SongSuggressHomeAdapter.ViewHolder> {

    SongSuggressHomeAdapterListener listener;


    public  interface SongSuggressHomeAdapterListener{
        void onClickItem(SongEntityModel model);
    }

    public void setListener(SongSuggressHomeAdapterListener listener) {
        this.listener = listener;
    }

    Context context;
    List<SongEntityModel> list;

    public SongSuggressHomeAdapter(Context context, List<SongEntityModel> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_item_song_goiy_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SongEntityModel entityModel = list.get(position);
        try{
            if (entityModel!=null){
                holder.nameSong.setText(entityModel.getSongEntity().getSongname());
                holder.singerName.setText(entityModel.getSingerEntityList().get(0).getSingername());
                Glide.with(context).load(entityModel.getSongEntity().getImg()).into(holder.imageSong);
            }
        }catch (Exception e){
            Log.e("Exxx",e.getMessage());
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
