package com.java.music.adapter.actor;

import android.content.Context;
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
import com.java.music.adapter.film.FilmHotAdapter;
import com.java.music.model.actor.ActorEntityModel;
import com.java.music.model.film.FilmEntityModel;

import java.util.List;

public class FilmActorAdapter extends RecyclerView.Adapter<FilmActorAdapter.ViewHolder> {

    FilmActorAdapterListener listener;


    public interface FilmActorAdapterListener {
        void onClickItem(ActorEntityModel model);
    }

    public void setListener(FilmActorAdapterListener listener) {
        this.listener = listener;
    }

    Context context;
    List<ActorEntityModel> list;

    public FilmActorAdapter(Context context, List<ActorEntityModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_item_actor_film, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ActorEntityModel entityModel = list.get(position);
        if (entityModel != null) {
            holder.txtAd.setText(entityModel.getFilmDTOList().get(0).getFilmEntity().getFilmname());
            //holder.txt.setText(entityModel.getFilmEntity().getFilmname());
            Glide.with(context).load(entityModel.getFilmDTOList().get(0).getFilmEntity().getImg()).into(holder.imgItem);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgItem;
        RelativeLayout layoutClick;
        TextView txt, txtAd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgItem = itemView.findViewById(R.id.imgItem);
            layoutClick = itemView.findViewById(R.id.layoutClick);
            txt = itemView.findViewById(R.id.txt);
            txtAd = itemView.findViewById(R.id.txtAd);

            layoutClick.setOnClickListener(v -> {
                if (listener!=null){
                    listener.onClickItem(list.get(getAdapterPosition()));
                }
            });
        }
    }
}