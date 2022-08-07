package com.rumibalkhi.quotipy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.rumibalkhi.quotipy.fragments.HomeFrag;
import com.rumibalkhi.quotipy.fragments.PoemFrag;
import com.rumibalkhi.quotipy.fragments.ProverbQuotes;
import com.rumibalkhi.quotipy.fragments.QuotesFrag;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    NavigationView nav;
    DrawerLayout drawerLayout;

    BottomNavigationView bottomNavigationView;
    ImageView share,fav;
    RelativeLayout JobTitles;
    AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);



        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        SharedPreferences prefs = getSharedPreferences("ADS", MODE_PRIVATE);
        String name = prefs.getString("showads", "true");

        mAdView = findViewById(R.id.adView);

        if(name.equals("true")){
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }


        bottomNavigationView = findViewById(R.id.bottom_nav);
        share = findViewById(R.id.share);
        fav = findViewById(R.id.fav);

        sideBar();
        setFrags();
        JobTitles = findViewById(R.id.JobTitles);
        JobTitles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JobTitles.setVisibility(View.GONE);

            }
        });

        TextView Share = findViewById(R.id.Share);
        TextView Favorite = findViewById(R.id.Favorite);
        TextView Feedback = findViewById(R.id.Feedback);
        TextView Rate = findViewById(R.id.Rate);
        TextView Privacy = findViewById(R.id.Privacy);
        ImageView optionMenu = findViewById(R.id.optionMenu);

        Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, "Hey buddy! Download *" +
                        getString(R.string.app_name) + "App* get Latest Quotes.. :\n\n https://play.google.com/store/apps/details?id=" +
                        getPackageName());
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                startActivity(Intent.createChooser(sharingIntent, "ChikuAI Code Dev. Team"));

            }
        });

        optionMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JobTitles.setVisibility(View.VISIBLE);


            }
        });

        Favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),FavoriteActivity.class));

            }
        });
        Feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),FeedbackActivity.class));

            }
        });
        Privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),PrivacyActivity.class));

            }
        });
        Rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String appPackageName = getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, "Hey buddy! Download *" +
                        getString(R.string.app_name) + "App* get Latest Quotes.. :\n\n https://play.google.com/store/apps/details?id=" +
                        getPackageName());
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                startActivity(Intent.createChooser(sharingIntent, "ChikuAI Code Dev. Team"));

            }
        });

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),FavoriteActivity.class));

            }
        });
    }

    private void setFrags() {

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFrag()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            Fragment selectedFragment = null;
            switch (item.getItemId()) {

                case R.id.bottom_home:
                    selectedFragment = new HomeFrag();
                    break;

                case R.id.bottom_poem:
                    selectedFragment = new PoemFrag();
                    break;

                case R.id.bottom_quotes:
                    selectedFragment = new QuotesFrag();
                    break;

                case R.id.bottom_proverb:
                    selectedFragment = new ProverbQuotes();
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    selectedFragment).commit();
            return true;

        });

    }


    private void sideBar() {


        nav = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        nav.setItemIconTintList(null);

        nav.setNavigationItemSelectedListener(menuItem -> {

            switch (menuItem.getItemId()) {

                case R.id.nav_share:

                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, "Hey buddy! Download *" +
                            getString(R.string.app_name) + "App* get Latest Quotes.. :\n\n https://play.google.com/store/apps/details?id=" +
                            getPackageName());
                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                    startActivity(Intent.createChooser(sharingIntent, "ChikuAI Code Dev. Team"));

                    break;


                case R.id.nav_rate:

                    final String appPackageName = getPackageName();
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    }

                    break;


                case R.id.nav_policy:

                    startActivity(new Intent(getApplicationContext(),PrivacyActivity.class));
                    break;

                case R.id.nav_feedback:

                    startActivity(new Intent(getApplicationContext(),FeedbackActivity.class));
                    break;
                case R.id.nav_fav:

                    startActivity(new Intent(getApplicationContext(),FavoriteActivity.class));
                    break;

                case R.id.nav_ads:

                    SharedPreferences.Editor editor = getSharedPreferences("ADS", MODE_PRIVATE).edit();
                    editor.putString("showads", "false");
                    editor.apply();
                    mAdView.setVisibility(View.GONE);
                    break;

            }

            if (drawerLayout.isDrawerOpen(nav)) {
                drawerLayout.closeDrawer(nav);

            } else {
                drawerLayout.openDrawer(nav);
            }
            return true;
        });

        ImageView navBtn = findViewById(R.id.openNav);
        navBtn.setOnClickListener(v->{
            if (drawerLayout.isDrawerOpen(nav)) {
                drawerLayout.closeDrawer(nav);
            } else {
                drawerLayout.openDrawer(nav);
            }
        });


    }





}