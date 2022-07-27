package com.rumibalkhi.quotipy.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rumibalkhi.quotipy.R;
import com.rumibalkhi.quotipy.interf.ClickSingle;
import com.rumibalkhi.quotipy.models.CategorySub;

import java.util.List;


public class CatsAdapter extends RecyclerView.Adapter<CatsAdapter.CatsHolder> {

    List<CategorySub> cats;
    ClickSingle clickSingle;


    public CatsAdapter(List<CategorySub> cats, ClickSingle clickSingle) {
        this.cats = cats;
        this.clickSingle = clickSingle;
    }

    @NonNull
    @Override
    public CatsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_sub, parent, false);

        return new CatsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatsHolder holder, int position) {

        CategorySub cat = cats.get(position);
        holder.cats.setText(cat.getCategory());

        holder.itemView.setOnClickListener(v -> clickSingle.click(cat.getCategory().toLowerCase()));

    }

    @Override
    public int getItemCount() {
        return cats.size();
    }

    public static class CatsHolder extends RecyclerView.ViewHolder {

        TextView cats;

        public CatsHolder(@NonNull View itemView) {
            super(itemView);

            cats = itemView.findViewById(R.id.sub_category);

        }
    }

}
