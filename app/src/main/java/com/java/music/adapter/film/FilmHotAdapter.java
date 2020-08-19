package com.java.music.adapter.film;

import android.content.Context;
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
import com.java.music.model.film.FilmEntityModel;

import java.util.List;

public class FilmHotAdapter extends RecyclerView.Adapter<FilmHotAdapter.ViewHolder> {

    FilmHotAdapterListener listener;


    public interface FilmHotAdapterListener {
        void onClickItem(FilmEntityModel model);
    }

    public void setListener(FilmHotAdapterListener listener) {
        this.listener = listener;
    }

    Context context;
    List<FilmEntityModel> list;

    public FilmHotAdapter(Context context, List<FilmEntityModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_item_film_hot, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FilmEntityModel entityModel = list.get(position);
        if (entityModel != null) {
            holder.txtNameFilm.setText(entityModel.getFilmEntity().getFilmname());
            Glide.with(context).load(entityModel.getFilmEntity().getImg()).error(R.drawable.imageloading).into(holder.imgItemFilm);
            String startTime = "00:00:00";
            int minutes = entityModel.getFilmEntity().getLength();
            int h = minutes / 60 + Integer.parseInt(startTime.substring(0,1));
            int m = minutes % 60 + Integer.parseInt(startTime.substring(3,4));
            String newtime = h+":"+m+":00";
            holder.time.setText(newtime);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgItemFilm;
        LinearLayout layoutClick;
        TextView time, txtNameFilm, txtAd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgItemFilm = itemView.findViewById(R.id.imgItemFilm);
            layoutClick = itemView.findViewById(R.id.layoutClick);
            time = itemView.findViewById(R.id.time);
            txtNameFilm = itemView.findViewById(R.id.txtNameFilm);
            txtAd = itemView.findViewById(R.id.txtAd);

            layoutClick.setOnClickListener(v -> {
                if (listener!=null){
                    listener.onClickItem(list.get(getAdapterPosition()));
                }
            });
        }
    }
}