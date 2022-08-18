package com.rumibalkhi.quotipy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rumibalkhi.quotipy.adapter.NewFavoriteAdapter;
import com.rumibalkhi.quotipy.adapter.NewPoemAdapter;
import com.rumibalkhi.quotipy.models.NewFavouriteModel;
import com.rumibalkhi.quotipy.models.NewPoemModel;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {



    RecyclerView recyclerView;
    NewFavoriteAdapter adapter;
    public List<NewFavouriteModel> poemModels = new ArrayList<>();
    SimpleDatabase2 simpleDatabase;
    AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);


        SearchView search = findViewById(R.id.search);
        recyclerView = findViewById(R.id.recycler_quotes);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.hasFixedSize();


        poemModels.clear();
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle((Html.fromHtml("<font color=\"#ffffff\">" + "Favorites" + "</font>")));
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        mAdView = findViewById(R.id.adView);




        SharedPreferences prefs = getSharedPreferences("ADS", MODE_PRIVATE);
        String name = prefs.getString("showads", "true");

        if(name.equals("false")){
            mAdView.setVisibility(View.GONE);
        }

        if(name.equals("true")){
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
            mAdView.setVisibility(View.VISIBLE);

        }


        simpleDatabase = new SimpleDatabase2(this);
        List<Note2> allNotes = simpleDatabase.getAllNotes();
        adapter = new NewFavoriteAdapter(this, allNotes);

        if(allNotes.isEmpty()){

        }else {
            recyclerView.setAdapter(adapter);

            adapter.notifyDataSetChanged();

        }




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
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


}