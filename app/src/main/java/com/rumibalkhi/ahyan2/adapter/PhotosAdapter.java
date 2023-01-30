package com.rumibalkhi.ahyan2.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rumibalkhi.ahyan2.Note2;
import com.rumibalkhi.ahyan2.R;
import com.rumibalkhi.ahyan2.SimpleDatabase2;
import com.rumibalkhi.ahyan2.models.NewPhotoModel;

import java.io.ByteArrayOutputStream;
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


            Bitmap icon = BitmapFactory.decodeResource(ctx.getResources(), list.get(position).getImg());
            saveImage(icon,ctx);
            //AltexImageDownloader.writeToDisk(ctx, list.get(position).getImg(), "IMAGES");

        });

        holder.fav.setOnClickListener(v -> {

            c = Calendar.getInstance();
            todaysDate = c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DAY_OF_MONTH);
            Log.d("DATE", "Date: "+todaysDate);
            currentTime = pad(c.get(Calendar.HOUR))+":"+pad(c.get(Calendar.MINUTE));
            Log.d("TIME", "Time: "+currentTime);

            Bitmap bitmap = BitmapFactory.decodeResource(ctx.getResources(), list.get(position).getImg());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] img = bos.toByteArray();

            Note2 note = new Note2(list.get(position).getName(),img,"true");
            SimpleDatabase2 sDB = new SimpleDatabase2(ctx);

            List<Note2> allNotes = sDB.getAllNotesCategory(list.get(position).getName());

            if(allNotes.isEmpty()){
                long id = sDB.addNote(note);
                Toast.makeText(ctx.getApplicationContext(), "Add to Favorites",Toast.LENGTH_SHORT).show();


            }else {


                Toast.makeText(ctx.getApplicationContext(), "Already Added to Favorites",Toast.LENGTH_SHORT).show();



            }



        });

        holder.share.setOnClickListener(v -> {

            Bitmap icon = BitmapFactory.decodeResource(ctx.getResources(), list.get(position).getImg());
            shareImage(icon,ctx);
//            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//            sharingIntent.setType("text/plain");
//            sharingIntent.putExtra(Intent.EXTRA_TEXT, "Hey buddy! *" +"Check this motivational quote"+list.get(position).getImg());
//            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
//            ctx.startActivity(Intent.createChooser(sharingIntent, "ChikuAI Code Dev. Team"));

        });


    }

    public void saveImage(Bitmap resource, Context activity) {
        String bitmapPath = MediaStore.Images.Media.insertImage(activity.getContentResolver(), resource, activity.getString(R.string.app_name), null);
        Uri bitmapUri = Uri.parse(bitmapPath);

    }
    public void shareImage(Bitmap resource, Context activity) {
        String bitmapPath = MediaStore.Images.Media.insertImage(activity.getContentResolver(), resource, activity.getString(R.string.app_name), null);
        Uri bitmapUri = Uri.parse(bitmapPath);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/png");


        intent.putExtra(Intent.EXTRA_TEXT, "Hey buddy! *" +"Check this motivational quote ");

        intent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
        activity.startActivity(Intent.createChooser(intent, "Share"));
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
