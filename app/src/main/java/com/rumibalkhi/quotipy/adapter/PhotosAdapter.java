package com.rumibalkhi.quotipy.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rumibalkhi.quotipy.R;
import com.rumibalkhi.quotipy.models.Photos;

import java.util.List;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotosHolder> {

    List<Photos> list;

    public PhotosAdapter(List<Photos> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public PhotosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PhotosHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_img,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PhotosHolder holder, int position) {

        Photos photos = list.get(position);

        Glide.with(holder.itemView.getContext())
                .load(photos.getUrl())
                .placeholder(R.drawable.custom_bg)
                .error(R.drawable.custom_bg)
                .into(holder.photo);


        holder.save.setOnClickListener(v -> {

        });

        holder.fav.setOnClickListener(v -> {


        });

        holder.share.setOnClickListener(v -> {


        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class PhotosHolder extends RecyclerView.ViewHolder{

        ImageView photo;
        LinearLayout share,save,fav;

        public PhotosHolder(@NonNull View itemView) {
            super(itemView);

            photo = itemView.findViewById(R.id.item_img_img);
            share = itemView.findViewById(R.id.item_img_share);
            save = itemView.findViewById(R.id.item_img_save);
            fav = itemView.findViewById(R.id.item_img_fav);


        }
    }
}
