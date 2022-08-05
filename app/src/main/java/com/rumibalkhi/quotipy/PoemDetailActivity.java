package com.rumibalkhi.quotipy;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

public class PoemDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poem_detail);

        String title = getIntent().getStringExtra("title");

        String text = getIntent().getStringExtra("text");


        TextView item_poem_quote = findViewById(R.id.item_poem_quote);
        item_poem_quote.setText(text);



        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle((Html.fromHtml("<font color=\"#ffffff\">" + title + "</font>")));
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


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