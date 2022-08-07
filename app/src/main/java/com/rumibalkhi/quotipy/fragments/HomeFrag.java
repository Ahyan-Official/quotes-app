package com.rumibalkhi.quotipy.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rumibalkhi.quotipy.R;
import com.rumibalkhi.quotipy.adapter.CatsAdapter;
import com.rumibalkhi.quotipy.adapter.NewPoemAdapter;
import com.rumibalkhi.quotipy.adapter.PhotosAdapter;
import com.rumibalkhi.quotipy.interf.ClickSingle;
import com.rumibalkhi.quotipy.models.CategorySub;
import com.rumibalkhi.quotipy.models.NewPhotoModel;
import com.rumibalkhi.quotipy.models.NewPoemModel;
import com.rumibalkhi.quotipy.models.Photos;

import java.util.ArrayList;
import java.util.List;


public class HomeFrag extends Fragment {

    View layout;

    RecyclerView recyclerView;
    PhotosAdapter adapter;
    public List<NewPhotoModel> poemModels = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_home, container, false);


        recyclerView = layout.findViewById(R.id.recycler_home);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.hasFixedSize();


        DatabaseReference dd = FirebaseDatabase.getInstance().getReference().child("photos");

        dd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                poemModels.clear();
                for (DataSnapshot dsp : snapshot.getChildren()) {

                    String text = dsp.child("img").getValue().toString();


                    NewPhotoModel qq = new NewPhotoModel(text);


                    poemModels.add(qq);


                }

                adapter = new PhotosAdapter( poemModels,getActivity());
                recyclerView.setAdapter(adapter);

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return layout;


    }
}