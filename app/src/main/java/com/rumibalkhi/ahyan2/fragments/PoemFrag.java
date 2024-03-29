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
import com.rumibalkhi.ahyan2.adapter.NewPoemAdapter;
import com.rumibalkhi.ahyan2.models.NewPoemModel;

import java.util.ArrayList;
import java.util.List;

public class PoemFrag extends Fragment {


    View layout;

    RecyclerView recyclerView;
    NewPoemAdapter adapter;
    public List<NewPoemModel> poemModels = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_poem, container, false);


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

        poemModels.clear();


        // DATA HERE//////////////////////////////////////////


        NewPoemModel qq = new NewPoemModel("Body my house\n" +
                "my horse my hound\n" +
                "what will I do\n" +
                "when you are fallen\n" +
                "\n" +
                "Where will I sleep\n" +
                "How will I ride\n" +
                "What will I hunt\n" +
                "\n" +
                "Where can I go\n" +
                "without my mount\n" +
                "all eager and quick\n" +
                "How will I know\n" +
                "in thicket ahead\n" +
                "is danger or treasure\n" +
                "when Body my good\n" +
                "bright dog is dead","May Swenson");


        poemModels.add(qq);
        //////////////////////////////////////////




        adapter = new NewPoemAdapter(getActivity(), poemModels);
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