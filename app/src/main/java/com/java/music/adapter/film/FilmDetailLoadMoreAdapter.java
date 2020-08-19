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
import com.java.music.model.film.FilmEntityModel;

import java.util.List;

public class FilmDetailLoadMoreAdapter extends RecyclerView.Adapter<FilmDetailLoadMoreAdapter.ViewHolder> {

    FilmDetailLoadMoreAdapterListener    listener;


    public  interface FilmDetailLoadMoreAdapterListener{
        void onClickItem(FilmEntityModel model);
    }

    public void setListener(FilmDetailLoadMoreAdapterListener listener) {
        this.listener = listener;
    }

    Context context;
    List<FilmEntityModel> list;

    public FilmDetailLoadMoreAdapter(Context context, List<FilmEntityModel> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_item_more_film_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FilmEntityModel entityModel = list.get(position);
        try{
            if (entityModel!=null){
                holder.filmNames.setText(entityModel.getFilmEntity().getFilmname());
                holder.country.setText(entityModel.getFilmEntity().getCountry());
                Glide.with(context).load(entityModel.getFilmEntity().getImg()).error(R.drawable.imageloading).into(holder.imageFilm);
                String startTime = "00:00:00";
                int minutes = entityModel.getFilmEntity().getLength();
                int h = minutes / 60 + Integer.parseInt(startTime.substring(0,1));
                int m = minutes % 60 + Integer.parseInt(startTime.substring(3,4));
                String newtime = h+":"+m+":00";
                holder.time.setText(newtime);
            }
        }catch (Exception e){
            Log.e("EE",e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageFilm;
        LinearLayout layoutClick;
        TextView time,filmNames,country,view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layoutClick = itemView.findViewById(R.id.layoutClick);
            imageFilm = itemView.findViewById(R.id.imageFilm);
            time = itemView.findViewById(R.id.time);
            filmNames = itemView.findViewById(R.id.filmNames);
            country = itemView.findViewById(R.id.country);
            view = itemView.findViewById(R.id.view);

            layoutClick.setOnClickListener(v -> {
                if (listener!=null){
                    listener.onClickItem(list.get(getAdapterPosition()));
                }
            });
        }
    }
}