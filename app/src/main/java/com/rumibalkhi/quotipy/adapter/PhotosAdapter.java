package com.rumibalkhi.quotipy.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rumibalkhi.quotipy.R;
import com.rumibalkhi.quotipy.models.NewPhotoModel;
import com.rumibalkhi.quotipy.models.Photos;

import java.util.List;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotosHolder> {

    List<NewPhotoModel> list;

    Context ctx;

    public PhotosAdapter(List<NewPhotoModel> list, Context ctx) {
        this.list = list;
        this.ctx = ctx;

    }

    @NonNull
    @Override
    public PhotosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PhotosHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_img,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PhotosHolder holder, int position) {

        Glide.with(holder.itemView.getContext())
                .load(list.get(position).getImg())
                .placeholder(R.drawable.custom_bg)
                .error(R.drawable.custom_bg)
                .into(holder.photo);


        holder.save.setOnClickListener(v -> {

            DatabaseReference aa = FirebaseDatabase.getInstance().getReference().child("favorite").push();
            aa.child("text").setValue(list.get(position).getImg()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {


                    Toast.makeText(ctx ,"Saved",Toast.LENGTH_LONG).show();

                }
            });
        });

        holder.fav.setOnClickListener(v -> {

            DatabaseReference aa = FirebaseDatabase.getInstance().getReference().child("favorite").push();
            aa.child("text").setValue(list.get(position).getImg()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {


                    Toast.makeText(ctx ,"Added to Favorite",Toast.LENGTH_LONG).show();

                }
            });


        });

        holder.share.setOnClickListener(v -> {

            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, "Hey buddy! *" +"Check this motivational quote"+list.get(position).getImg());
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
            ctx.startActivity(Intent.createChooser(sharingIntent, "ChikuAI Code Dev. Team"));

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
