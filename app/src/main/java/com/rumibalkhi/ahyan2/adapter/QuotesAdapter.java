package com.rumibalkhi.ahyan2.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rumibalkhi.ahyan2.R;
import com.rumibalkhi.ahyan2.models.Quotes;

import java.util.List;

public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.QuotesHolder> {

    List<Quotes> list;

    public QuotesAdapter(List<Quotes> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public QuotesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new QuotesHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quotes, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull QuotesHolder holder, int position) {

        Quotes quotes = list.get(position);


        if (quotes.getBg().contains("#")) {
            try {
                holder.bg.setBackgroundColor(Color.parseColor(quotes.getBg()));
            } catch (Exception e) {
                setBgColor(holder.bg, position);
            }

        } else {
            setBgColor(holder.bg, position);
        }


        holder.quotes.setText(quotes.getQuotes());


        holder.save.setOnClickListener(v -> {

        });

        holder.copy.setOnClickListener(v -> {


        });

        holder.share.setOnClickListener(v -> {


        });


    }

    private void setBgColor(RelativeLayout bg, int pos) {
        String[] colors = {"#061a3b", "#063b36", "#2b0a21", "#282b0a", "#090e73", "#097357", "#201221"};
        int colorPos = pos % colors.length;
        bg.setBackgroundColor(Color.parseColor(colors[colorPos]));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class QuotesHolder extends RecyclerView.ViewHolder {

        TextView quotes;
        LinearLayout save, copy, share;
        RelativeLayout bg;

        public QuotesHolder(@NonNull View itemView) {
            super(itemView);

            quotes = itemView.findViewById(R.id.item_quotes_quote);
            save = itemView.findViewById(R.id.item_quotes_save);
            share = itemView.findViewById(R.id.item_quotes_share);
            copy = itemView.findViewById(R.id.item_quotes_copy);
            bg = itemView.findViewById(R.id.item_quotes_bg);


        }
    }

}
