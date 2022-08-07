package com.rumibalkhi.quotipy.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rumibalkhi.quotipy.PoemDetailActivity;
import com.rumibalkhi.quotipy.R;
import com.rumibalkhi.quotipy.models.NewFavouriteModel;
import com.rumibalkhi.quotipy.models.NewPoemModel;

import java.util.ArrayList;
import java.util.List;

public class NewFavoriteAdapter extends RecyclerView.Adapter<NewFavoriteAdapter.ViewHolder> implements Filterable {

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    List<NewFavouriteModel> stationList2;
    List<NewFavouriteModel> stationList2Full;
    Context context;


    // data is passed into the constructor
    public NewFavoriteAdapter(Context context, List<NewFavouriteModel> data) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        stationList2 = data;
        stationList2Full = new ArrayList<>(data);

    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_new_favorite, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {


        if(stationList2.get(position).getName().startsWith("http")){

            holder.item_img_img.setVisibility(View.VISIBLE);

            Glide.with(holder.itemView.getContext())
                    .load(stationList2.get(position).getName())
                    .placeholder(R.drawable.custom_bg)
                    .error(R.drawable.custom_bg)
                    .into(holder.item_img_img);

            holder.tvName.setVisibility(View.GONE);

        }else{

            holder.tvName.setVisibility(View.VISIBLE);
            holder.item_img_img.setVisibility(View.GONE);

            String name = stationList2.get(position).getName();
            holder.tvName.setText(name);

        }





        //on clicking layout
//        holder.layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                Intent intent = new Intent(context, PoemDetailActivity.class);
//                intent.putExtra("text", stationList2.get(position).getText());
//                intent.putExtra("title", stationList2.get(position).getTitle());
//
//
//                context.startActivity(intent);
//
//            }
//        });

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
            List<NewFavouriteModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(stationList2Full);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (NewFavouriteModel item : stationList2Full) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
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
        LinearLayout layout;

        ImageView item_img_img;

        ViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.item_fav);
            layout = itemView.findViewById(R.id.layout);
            item_img_img = itemView.findViewById(R.id.item_img_img);


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