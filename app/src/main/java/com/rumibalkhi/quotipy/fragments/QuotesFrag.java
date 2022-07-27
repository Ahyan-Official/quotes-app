package com.rumibalkhi.quotipy.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rumibalkhi.quotipy.R;
import com.rumibalkhi.quotipy.adapter.QuotesAdapter;
import com.rumibalkhi.quotipy.models.Quotes;

import java.util.ArrayList;
import java.util.List;


public class QuotesFrag extends Fragment {

    View layout;

    RecyclerView recyclerView;
    QuotesAdapter adapter;
    List<Quotes> list = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        layout = inflater.inflate(R.layout.fragment_quotes, container, false);

        recyclerView = layout.findViewById(R.id.recycler_quotes);

        setData();


        return layout;
    }

    private void setData() {

        list.add(new Quotes("","“The purpose of our lives is to be happy.”"));
        list.add(new Quotes("","“Life is what happens when you’re busy making other plans.”"));
        list.add(new Quotes("","“Get busy living or get busy dying.” "));
        list.add(new Quotes("","“Many of life’s failures are people who did not realize how close they were to success when they gave up.”"));
        list.add(new Quotes("","“If you want to live a happy life," +
                "tie it to a goal, not to people or things.”–"));
        list.add(new Quotes("","“Money and success don’t change people;" +
                "they merely amplify what is already there.” "));
        list.add(new Quotes("","“Your time is limited," +
                "so don’t waste it living someone else’s life. " +
                "Don’t be trapped by dogma – which is living with the results of other people’s thinking.” "));

        adapter = new QuotesAdapter(list);
        recyclerView.setAdapter(adapter);



    }
}