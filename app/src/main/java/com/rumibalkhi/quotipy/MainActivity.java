package com.rumibalkhi.quotipy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.rumibalkhi.quotipy.fragments.HomeFrag;
import com.rumibalkhi.quotipy.fragments.PoemFrag;
import com.rumibalkhi.quotipy.fragments.ProverbQuotes;
import com.rumibalkhi.quotipy.fragments.QuotesFrag;

public class MainActivity extends AppCompatActivity {

    NavigationView nav;
    DrawerLayout drawerLayout;

    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);

        bottomNavigationView = findViewById(R.id.bottom_nav);

        sideBar();
        setFrags();

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