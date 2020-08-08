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
import com.java.music.model.singer.SingerEntityModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SingerPageSongAdapter  extends RecyclerView.Adapter<SingerPageSongAdapter.ViewHolder> {

    SingerPageSongAdapterListener listener;


    public interface SingerPageSongAdapterListener {
        void onClickItem(SingerEntityModel model);
    }

    public void setListener(SingerPageSongAdapterListener listener) {
        this.listener = listener;
    }

    Context context;
    List<SingerEntityModel> list;

    public SingerPageSongAdapter(Context context, List<SingerEntityModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_singer_page, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SingerEntityModel entityModel = list.get(position);
        try {
            if (entityModel != null) {
                holder.txtName.setText(entityModel.getSingerEntity().getSingername());
                Glide.with(context).load(entityModel.getSingerEntity().getImg()).into(holder.imgItem);
            }
        } catch (Exception e) {
            Log.e("Exx", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        if (list.size() > 10) {
            return 10;
        } else {
            return list.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgItem;
        TextView txtName;
        LinearLayout layoutClick;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layoutClick = itemView.findViewById(R.id.layoutClick);
            imgItem = itemView.findViewById(R.id.imgItem);
            txtName = itemView.findViewById(R.id.txtName);

            layoutClick.setOnClickListener(v -> {
                if (listener!=null){
                    listener.onClickItem(list.get(getAdapterPosition()));
                }
            });
        }
    }
}