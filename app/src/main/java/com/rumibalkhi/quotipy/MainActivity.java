package com.rumibalkhi.quotipy;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.rumibalkhi.quotipy.fragments.BioFrag;
import com.rumibalkhi.quotipy.fragments.HomeFrag;
import com.rumibalkhi.quotipy.fragments.PoemFrag;
import com.rumibalkhi.quotipy.fragments.ProverbQuotes;
import com.rumibalkhi.quotipy.fragments.QuotesFrag;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    NavigationView nav;
    DrawerLayout drawerLayout;

    BottomNavigationView bottomNavigationView;
    ImageView share,fav;
    RelativeLayout JobTitles;
    AdView mAdView;
    String name;
    private InterstitialAd interstitial;

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);



        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        isWriteStoragePermissionGranted();

        SharedPreferences prefs = getSharedPreferences("ADS", MODE_PRIVATE);
        name = prefs.getString("showads", "true");

        mAdView = findViewById(R.id.adView);

        if(name.equals("true")){
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }



            MobileAds.initialize(this, getString(R.string.admob_app_id));
            AdRequest adIRequest = new AdRequest.Builder().build();

            // Prepare the Interstitial Ad Activity
            interstitial = new InterstitialAd(MainActivity.this);

            // Insert the Ad Unit ID
            interstitial.setAdUnitId(getString(R.string.admob_interstitial_id));

            // Interstitial Ad load Request
            interstitial.loadAd(adIRequest);

            // Prepare an Interstitial Ad Listener
            interstitial.setAdListener(new AdListener()
            {
                public void onAdLoaded()
                {
                    // Call displayInterstitial() function when the Ad loads
                }
            });




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
                JobTitles.setVisibility(View.GONE);

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","abc@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Enter your feedback here");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));


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

                JobTitles.setVisibility(View.GONE);

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

//        Bitmap icon = BitmapFactory.decodeResource(this.getResources(),
//                R.drawable.image);
//        Intent share = new Intent(Intent.ACTION_SEND);
//        share.setType("image/jpeg");
//
//        ContentValues values = new ContentValues();
//        values.put(MediaStore.Images.Media.TITLE, "title");
//        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
//        Uri uri = getContentResolver().insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
//                values);
//
//
//        OutputStream outstream;
//        try {
//            outstream = getContentResolver().openOutputStream(uri);
//            icon.compress(Bitmap.CompressFormat.JPEG, 100, outstream);
//            outstream.close();
//        } catch (Exception e) {
//            System.err.println(e.toString());
//        }
//
//        share.putExtra(Intent.EXTRA_STREAM, uri);
//        startActivity(Intent.createChooser(share, "Share Image"));


    }

    public void shareDrawable(Context context, int resourceId, String fileName) {
        try {
            //convert drawable resource to bitmap
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);

            //save bitmap to app cache folder
            File outputFile = new File(context.getCacheDir(), fileName + ".png");
            FileOutputStream outPutStream = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outPutStream);
            outPutStream.flush();
            outPutStream.close();
            outputFile.setReadable(true, false);

            //share file
            Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(outputFile));
            shareIntent.setType("image/png");
            context.startActivity(shareIntent);
        }
        catch (Exception e) {
            Toast.makeText(context, "error", Toast.LENGTH_LONG).show();
        }
    }
    public void displayInterstitial()
    {
        // If Interstitial Ads are loaded then show else show nothing.
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
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

                    if(name.equals("true")) {
                        displayInterstitial();
                    }



                    selectedFragment = new PoemFrag();
                    break;

                case R.id.bottom_quotes:

                    if(name.equals("true")){
                        displayInterstitial();

                    }


                    selectedFragment = new QuotesFrag();
                    break;

                case R.id.bottom_proverb:

                    selectedFragment = new ProverbQuotes();
                    break;
                case R.id.bottom_bio:

                    selectedFragment = new BioFrag();
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

                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","abc@gmail.com", null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Enter your feedback here");
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));

                    //startActivity(new Intent(getApplicationContext(),FeedbackActivity.class));
                    break;
                case R.id.nav_fav:

                    startActivity(new Intent(getApplicationContext(),FavoriteActivity.class));
                    break;

                case R.id.nav_ads:

                    finish();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    overridePendingTransition(0, 0);

                    SharedPreferences.Editor editor = getSharedPreferences("ADS", MODE_PRIVATE).edit();
                    editor.putString("showads", "false");
                    editor.apply();
                    mAdView.setVisibility(View.GONE);
                    break;

                case R.id.nav_web:


                    startActivity(new Intent(getApplicationContext(),WebViewActivity.class));
                    overridePendingTransition(0, 0);



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



    public  boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //Log.v(TAG,"Permission is granted2");
                return true;
            } else {

                //Log.v(TAG,"Permission is revoked2");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 3);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            //Log.v(TAG,"Permission is granted2");
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(         MainActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){

                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case 3:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {


                    }
                    //startLocationUpdates();
                } else {
                    // Permission Denied
                }
                break;
            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        //mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

                    }
                    //startLocationUpdates();
                } else {
                    // Permission Denied
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }
}