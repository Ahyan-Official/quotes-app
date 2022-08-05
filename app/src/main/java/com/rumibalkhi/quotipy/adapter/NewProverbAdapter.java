package com.rumibalkhi.quotipy.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rumibalkhi.quotipy.R;
import com.rumibalkhi.quotipy.models.NewProverbModel;
import com.rumibalkhi.quotipy.models.NewQuotesModel;

import java.util.ArrayList;
import java.util.List;

public class NewProverbAdapter extends RecyclerView.Adapter<NewProverbAdapter.ViewHolder> implements Filterable {

    private LayoutInflater mInflater;
    private NewQuotesAdapter.ItemClickListener mClickListener;
    List<NewProverbModel> stationList2;
    List<NewProverbModel> stationList2Full;
    Context context;


    // data is passed into the constructor
    public NewProverbAdapter(Context context, List<NewProverbModel> data) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        stationList2 = data;
        stationList2Full = new ArrayList<>(data);

    }

    // inflates the row layout from xml when needed
    @Override
    public NewProverbAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_new_proverb, parent, false);
        return new NewProverbAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(NewProverbAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String name = stationList2.get(position).getText();



        holder.tvName.setText(name);


        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                DatabaseReference aa = FirebaseDatabase.getInstance().getReference().child("favorite").push();
                aa.child("text").setValue(stationList2.get(position).getText()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {


                        Toast.makeText(context.getApplicationContext() ,"Added to Favorite",Toast.LENGTH_LONG).show();
                    }
                });
                return false;
            }
        });

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
            List<NewProverbModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(stationList2Full);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (NewProverbModel item : stationList2Full) {
                    if (item.getText().toLowerCase().contains(filterPattern)) {
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

        ViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.item_proverb_proverb);
            layout = itemView.findViewById(R.id.layout);


            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }



    // allows clicks events to be caught
    void setClickListener(NewQuotesAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


}
