package com.java.music.adapter.film;

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
import com.java.music.model.film.FilmEntityModel;

import java.util.List;

public class FilmOfActorAdapter extends RecyclerView.Adapter<FilmOfActorAdapter.ViewHolder> {

    FilmActorAdapterListener listener;


    public interface FilmActorAdapterListener {
        void onClickItem(FilmEntityModel model);
    }

    public void setListener(FilmActorAdapterListener listener) {
        this.listener = listener;
    }

    Context context;
    List<FilmEntityModel> list;

    public FilmOfActorAdapter(Context context, List<FilmEntityModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_film_suggestion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FilmEntityModel entityModel = list.get(position);
        if (entityModel!=null || entityModel.getFilmEntity() !=null){
            holder.txtNameFilm.setText(entityModel.getFilmEntity().getFilmname());
            String startTime = "00:00:00";
            int minutes = entityModel.getFilmEntity().getLength();
            int h = minutes / 60 + Integer.parseInt(startTime.substring(0,1));
            int m = minutes % 60 + Integer.parseInt(startTime.substring(3,4));
            String newtime = h+":"+m+":00";
            holder.countryName.setText(newtime);
            if (entityModel.getFilmEntity().getImg()!=null){
                Glide.with(context).load(entityModel.getFilmEntity().getImg()).error(R.drawable.imageloading).into(holder.imgItemFilm);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgItemFilm;
        RelativeLayout layoutLine;
        TextView txtNameFilm,countryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layoutLine = itemView.findViewById(R.id.layoutLine);
            imgItemFilm = itemView.findViewById(R.id.imgItemFilm);
            txtNameFilm = itemView.findViewById(R.id.txtNameFilm);
            countryName = itemView.findViewById(R.id.time);

            layoutLine.setOnClickListener(v -> {
                if (listener!=null){
                    listener.onClickItem(list.get(getAdapterPosition()));
                }
            });
        }
    }
}