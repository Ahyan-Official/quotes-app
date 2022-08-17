package com.rumibalkhi.quotipy.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.rumibalkhi.quotipy.R;
import com.rumibalkhi.quotipy.adapter.CatsAdapter;
import com.rumibalkhi.quotipy.adapter.NewPoemAdapter;
import com.rumibalkhi.quotipy.adapter.PhotosAdapter;
import com.rumibalkhi.quotipy.interf.ClickSingle;
import com.rumibalkhi.quotipy.models.CategorySub;
import com.rumibalkhi.quotipy.models.NewPhotoModel;
import com.rumibalkhi.quotipy.models.NewPoemModel;
import com.rumibalkhi.quotipy.models.Photos;

import java.io.File;
import java.io.IOException;
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

        SharedPreferences prefs = getActivity().getSharedPreferences("ADS", MODE_PRIVATE);
        String name = prefs.getString("showads", "true");
        ImageView aa = layout.findViewById(R.id.bottom);
        if(name.equals("false")){

            aa.setVisibility(View.GONE);
        }


        poemModels.clear();



        // DATA HERE//////////////////////////////////////////
        NewPhotoModel qq = new NewPhotoModel(R.drawable.images,"image2");


        poemModels.add(qq);

        ////////////////////////////
        adapter = new PhotosAdapter( poemModels,getActivity());
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        return layout;




    }


    private StorageReference storageReference;


    String filename1;

}