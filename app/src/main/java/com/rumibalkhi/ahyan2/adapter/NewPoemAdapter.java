package com.rumibalkhi.ahyan2.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.rumibalkhi.ahyan2.Note2;
import com.rumibalkhi.ahyan2.PoemDetailActivity;
import com.rumibalkhi.ahyan2.R;
import com.rumibalkhi.ahyan2.SimpleDatabase2;
import com.rumibalkhi.ahyan2.models.NewPoemModel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NewPoemAdapter extends RecyclerView.Adapter<NewPoemAdapter.ViewHolder> implements Filterable {

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    List<NewPoemModel> stationList2;
    List<NewPoemModel> stationList2Full;
    Context context;

    Calendar c;
    String todaysDate;
    String currentTime;
    // data is passed into the constructor
    public NewPoemAdapter(Context context, List<NewPoemModel> data) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        stationList2 = data;
        stationList2Full = new ArrayList<>(data);

    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_new_poem, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String name = stationList2.get(position).getTitle();



        holder.tvName.setText(name);


        //on clicking layout
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(context, PoemDetailActivity.class);
                intent.putExtra("text", stationList2.get(position).getText());
                intent.putExtra("title", stationList2.get(position).getTitle());


                context.startActivity(intent);

            }
        });

        holder.item_img_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                c = Calendar.getInstance();
                todaysDate = c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DAY_OF_MONTH);
                Log.d("DATE", "Date: "+todaysDate);
                currentTime = pad(c.get(Calendar.HOUR))+":"+pad(c.get(Calendar.MINUTE));
                Log.d("TIME", "Time: "+currentTime);

                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.images);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                byte[] img = bos.toByteArray();

                Note2 note = new Note2(stationList2.get(position).getTitle(),img,"poem"+stationList2.get(position).getText());
                SimpleDatabase2 sDB = new SimpleDatabase2(context);

                List<Note2> allNotes = sDB.getAllNotesCategory(stationList2.get(position).getText());

                if(allNotes.isEmpty()){
                    long id = sDB.addNote(note);
                    Toast.makeText(context.getApplicationContext(), "Add to Favorites",Toast.LENGTH_SHORT).show();


                }else {


                    Toast.makeText(context.getApplicationContext(), "Already Added to Favorites",Toast.LENGTH_SHORT).show();



                }



            }
        });

        holder.item_quotes_share.setOnClickListener(v -> {

            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, "Hey buddy! *" +"Check this Poem "+name+"\n"+stationList2.get(position).getText());
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
            context.startActivity(Intent.createChooser(sharingIntent, "ChikuAI Code Dev. Team"));

        });

    }
    private String pad(int time) {
        if(time < 10)
            return "0"+time;
        return String.valueOf(time);

    }
    // total number of rows
    @Override
    public int getItemCount() {
        return stationList2.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<NewPoemModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(stationList2Full);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (NewPoemModel item : stationList2Full) {
                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            stationList2.clear();
            stationList2.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName;
        ImageView img;
        LinearLayout layout,item_img_fav,item_quotes_share;

        ViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.item_quotes_quote);
            layout = itemView.findViewById(R.id.layout);
            item_img_fav = itemView.findViewById(R.id.item_img_fav);
            item_quotes_share = itemView.findViewById(R.id.item_quotes_share);


            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }



    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }




}