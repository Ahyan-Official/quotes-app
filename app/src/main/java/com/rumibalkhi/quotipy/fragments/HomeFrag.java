package com.rumibalkhi.quotipy.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.rumibalkhi.quotipy.R;
import com.rumibalkhi.quotipy.adapter.CatsAdapter;
import com.rumibalkhi.quotipy.adapter.PhotosAdapter;
import com.rumibalkhi.quotipy.interf.ClickSingle;
import com.rumibalkhi.quotipy.models.CategorySub;
import com.rumibalkhi.quotipy.models.Photos;

import java.util.ArrayList;
import java.util.List;


public class HomeFrag extends Fragment {

    View layout;

    RecyclerView catRecycler, recyclerView;
    List<Photos> list = new ArrayList<>();
    PhotosAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = layout.findViewById(R.id.recycler_home);


        loadContent();


        return layout;
    }

    private void loadContent() {

        list.add(new Photos("https://wallpaper.dog/large/5464797.jpg"));
        list.add(new Photos("https://wallup.net/wp-content/uploads/2016/01/142303-black_background-typography-quote-minimalism-748x421.jpg"));
        list.add(new Photos("https://c4.wallpaperflare.com/wallpaper/807/298/411/quote-green-typography-motivational-wallpaper-preview.jpg"));
        list.add(new Photos("https://skyprep.com/wp-content/uploads/2013/07/Alfred_Quotes1.jpg"));
        list.add(new Photos("https://www.goalcast.com/wp-content/uploads/2017/06/Steve-Jobs-quotes-on-being-yourself-Time-is-limited.jpg"));
        list.add(new Photos("https://altarekacademy.com/wp-content/uploads/2021/05/04_Confidence_Quotes_Quotes_A_man_cannot-1068x561-1.jpg"));
        list.add(new Photos("https://www.azquotes.com/vangogh-image-quotes/8/92/Quotation-Ralph-Waldo-Emerson-To-be-yourself-in-a-world-that-is-constantly-trying-8-92-43.jpg"));

        adapter = new PhotosAdapter(list);
        recyclerView.setAdapter(adapter);


    }
}