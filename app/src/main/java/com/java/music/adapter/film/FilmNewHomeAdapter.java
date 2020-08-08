package com.java.music.adapter.film;

import android.content.Context;
import android.os.CountDownTimer;
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
import com.java.music.adapter.song.SongHomeAdapter;
import com.java.music.model.film.FilmEntityModel;
import com.java.music.model.song.SongEntityModel;

import java.util.List;

public class FilmNewHomeAdapter  extends RecyclerView.Adapter<FilmNewHomeAdapter.ViewHolder> {

    FilmNewHomeAdapterListener listener;


    public  interface FilmNewHomeAdapterListener{
        void onClickItem(FilmEntityModel model);
    }

    public void setListener(FilmNewHomeAdapterListener listener) {
        this.listener = listener;
    }

    Context context;
    List<FilmEntityModel> list;

    public FilmNewHomeAdapter(Context context, List<FilmEntityModel> list) {
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
        if (entityModel!=null){
            holder.txtNameFilm.setText(entityModel.getFilmEntity().getFilmname());
            String startTime = "00:00:00";
            int minutes = entityModel.getFilmEntity().getLength();
            int h = minutes / 60 + Integer.parseInt(startTime.substring(0,1));
            int m = minutes % 60 + Integer.parseInt(startTime.substring(3,4));
            String newtime = h+":"+m+":00";
            holder.time.setText(newtime);
            Glide.with(context).load(entityModel.getFilmEntity().getImg()).into(holder.imgItemFilm);
        }
    }

    @Override
    public int getItemCount() {
        if (list.size()>10){
            return 10;
        }
        else {
            return list.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgItemFilm;
        LinearLayout layoutClick;
        TextView time,txtNameFilm,txtAd;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layoutClick = itemView.findViewById(R.id.layoutClick);
            imgItemFilm = itemView.findViewById(R.id.imgItemFilm);
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
