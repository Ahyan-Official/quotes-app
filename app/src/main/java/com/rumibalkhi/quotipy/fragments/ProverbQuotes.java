package com.rumibalkhi.quotipy.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rumibalkhi.quotipy.R;
import com.rumibalkhi.quotipy.adapter.NewProverbAdapter;
import com.rumibalkhi.quotipy.adapter.NewQuotesAdapter;
import com.rumibalkhi.quotipy.models.NewProverbModel;
import com.rumibalkhi.quotipy.models.NewQuotesModel;

import java.util.ArrayList;
import java.util.List;


public class ProverbQuotes extends Fragment {


    View layout;

    RecyclerView recyclerView;
    NewProverbAdapter adapter;
    public List<NewProverbModel> quotesModels = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        layout = inflater.inflate(R.layout.fragment_proverb_quotes, container, false);


        SearchView search = layout.findViewById(R.id.search);
        recyclerView = layout.findViewById(R.id.recycler_quotes);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.hasFixedSize();

        search.setQueryHint("Custom Search Hint");

        SharedPreferences prefs = getActivity().getSharedPreferences("ADS", MODE_PRIVATE);
        String name = prefs.getString("showads", "true");
        ImageView aa = layout.findViewById(R.id.bottom);
        if(name.equals("false")){

            aa.setVisibility(View.GONE);
        }


        quotesModels.clear();


        //ADD DATA HERE ////////////////////////////////////////
        NewProverbModel qq = new NewProverbModel("asdasdasdasd");


        quotesModels.add(qq);
        // ////////////////////////////////////////

        adapter = new NewProverbAdapter(getActivity(), quotesModels);
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
