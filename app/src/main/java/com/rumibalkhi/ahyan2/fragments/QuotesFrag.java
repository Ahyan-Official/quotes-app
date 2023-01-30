package com.rumibalkhi.ahyan2.fragments;


import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;

import com.rumibalkhi.ahyan2.R;
import com.rumibalkhi.ahyan2.adapter.NewQuotesAdapter;
import com.rumibalkhi.ahyan2.models.NewQuotesModel;

import java.util.ArrayList;
import java.util.List;


public class QuotesFrag extends Fragment {

    View layout;

    RecyclerView recyclerView;
    NewQuotesAdapter adapter;
    public List<NewQuotesModel> quotesModels = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        layout = inflater.inflate(R.layout.fragment_quotes, container, false);


        SearchView search = layout.findViewById(R.id.search);
        recyclerView = layout.findViewById(R.id.recycler_quotes);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.hasFixedSize();



        SharedPreferences prefs = getActivity().getSharedPreferences("ADS", MODE_PRIVATE);
        String name = prefs.getString("showads", "true");
        ImageView aa = layout.findViewById(R.id.bottom);
        if(name.equals("false")){

            aa.setVisibility(View.GONE);
        }


        quotesModels.clear();


        //ADD DATA HERE ////////////////////////////////////////

        NewQuotesModel q1 = new NewQuotesModel("Hi new cowawdasdd2");
        NewQuotesModel q2 = new NewQuotesModel("Hi new cowawdasdd23");
        NewQuotesModel q3 = new NewQuotesModel("Hi new cowawdasd8");
        NewQuotesModel q4 = new NewQuotesModel("Hi new cowawdasd6");
        NewQuotesModel q5 = new NewQuotesModel("Hi new cowawdasd4");
        NewQuotesModel q6 = new NewQuotesModel("Hi new cowawdasd9");
        NewQuotesModel q7 = new NewQuotesModel("Hi new cowawdasd567");
        NewQuotesModel q8 = new NewQuotesModel("Hi new cowawdasd256");

        quotesModels.add(q1);
        quotesModels.add(q2);
        quotesModels.add(q3);
        quotesModels.add(q4);
        quotesModels.add(q5);
        quotesModels.add(q6);
        quotesModels.add(q7);
        quotesModels.add(q8);
        // ////////////////////////////////////////

        adapter = new NewQuotesAdapter(getActivity(), quotesModels);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();



        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                recyclerView.setVisibility(View.VISIBLE);
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return layout;




    }




}