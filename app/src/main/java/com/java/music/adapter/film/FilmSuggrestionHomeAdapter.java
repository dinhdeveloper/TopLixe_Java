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

public class FilmSuggrestionHomeAdapter  extends RecyclerView.Adapter<FilmSuggrestionHomeAdapter.ViewHolder> {

    FilmSuggrestionHomeAdapterListener listener;


    public  interface FilmSuggrestionHomeAdapterListener{
        void onClickItem(FilmEntityModel model);
    }

    public void setListener(FilmSuggrestionHomeAdapterListener listener) {
        this.listener = listener;
    }

    Context context;
    List<FilmEntityModel> list;
    FilmEntityModel entityModel;

    public FilmSuggrestionHomeAdapter(Context context, List<FilmEntityModel> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_item_film_load_more, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        entityModel = list.get(position);
        if (entityModel!=null){
            holder.nameFilm.setText(entityModel.getFilmEntity().getFilmname());
            String startTime = "00:00:00";
            int minutes = entityModel.getFilmEntity().getLength();
            int h = minutes / 60 + Integer.parseInt(startTime.substring(0,1));
            int m = minutes % 60 + Integer.parseInt(startTime.substring(3,4));
            String newtime = h+":"+m+":00";
            holder.countryName.setText(newtime);
            Glide.with(context).load(entityModel.getFilmEntity().getImg()).into(holder.imageFilm);
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
        ImageView imageFilm;
        LinearLayout layoutClick;
        TextView nameFilm,countryName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layoutClick = itemView.findViewById(R.id.layoutClick);
            imageFilm = itemView.findViewById(R.id.imageFilm);
            nameFilm = itemView.findViewById(R.id.nameFilm);
            countryName = itemView.findViewById(R.id.countryName);

            layoutClick.setOnClickListener(v -> {
                if (listener!=null){
                    listener.onClickItem(list.get(getAdapterPosition()));
                }
            });
        }
    }
}
