package com.rumibalkhi.quotipy.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.artjimlop.altex.AltexImageDownloader;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rumibalkhi.quotipy.Note;
import com.rumibalkhi.quotipy.R;
import com.rumibalkhi.quotipy.SimpleDatabase;
import com.rumibalkhi.quotipy.models.NewPhotoModel;
import com.rumibalkhi.quotipy.models.Photos;

import java.util.Calendar;
import java.util.List;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotosHolder> {

    List<NewPhotoModel> list;

    Context ctx;
    Calendar c;
    String todaysDate;
    String currentTime;
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
    public void onBindViewHolder(@NonNull PhotosHolder holder, @SuppressLint("RecyclerView") int position) {

        Glide.with(holder.itemView.getContext())
                .load(list.get(position).getImg())
                .placeholder(R.drawable.custom_bg)
                .error(R.drawable.custom_bg)
                .into(holder.photo);


        holder.save.setOnClickListener(v -> {

            Toast.makeText(ctx.getApplicationContext(), "Downloading",Toast.LENGTH_SHORT).show();
            AltexImageDownloader.writeToDisk(ctx, list.get(position).getImg(), "IMAGES");
        });

        holder.fav.setOnClickListener(v -> {

            c = Calendar.getInstance();
            todaysDate = c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DAY_OF_MONTH);
            Log.d("DATE", "Date: "+todaysDate);
            currentTime = pad(c.get(Calendar.HOUR))+":"+pad(c.get(Calendar.MINUTE));
            Log.d("TIME", "Time: "+currentTime);

            Note note = new Note(list.get(position).getImg(),list.get(position).getImg(),todaysDate,currentTime);
            SimpleDatabase sDB = new SimpleDatabase(ctx);

            List<Note> allNotes = sDB.getAllNotesCategory(list.get(position).getImg());

            if(allNotes.isEmpty()){
                long id = sDB.addNote(note);
                Toast.makeText(ctx.getApplicationContext(), "Add to Favorites",Toast.LENGTH_SHORT).show();


            }else {


                Toast.makeText(ctx.getApplicationContext(), "Already Added to Favorites",Toast.LENGTH_SHORT).show();



            }



        });

        holder.share.setOnClickListener(v -> {

            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, "Hey buddy! *" +"Check this motivational quote"+list.get(position).getImg());
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
            ctx.startActivity(Intent.createChooser(sharingIntent, "ChikuAI Code Dev. Team"));

        });


    }
    private String pad(int time) {
        if(time < 10)
            return "0"+time;
        return String.valueOf(time);

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
