package com.fjord.podrozni;


import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;

import com.fjord.podrozni.Fragments.AktualnosciFragment;
import com.fjord.podrozni.Fragments.Dzien1Fragment;
import com.fjord.podrozni.Fragments.Dzien2Fragment;
import com.fjord.podrozni.Fragments.Dzien3Fragment;
import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new Dzien1Fragment(), "Dzień 1");
        viewPagerAdapter.addFragments(new Dzien2Fragment(), "Dzień 2");
        viewPagerAdapter.addFragments(new Dzien3Fragment(), "Dzień 3");
        viewPagerAdapter.addFragments(new AktualnosciFragment(), "News");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(actionBarDrawerToggle);

        drawer.openDrawer(GravityCompat.START);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_gmap:
                        goToMap();
                        return false;
                    case R.id.nav_map:
                        showMapPop();
                        return false;
                    case R.id.nav_zab:
                        goToWww(getString(R.string.zabkowicelWWW));
                        return true;
                    case R.id.nav_home:
                        goToWww(getString(R.string.festivalWWW));
                        return true;
                    case R.id.nav_FB:
                        goToWww(getString(R.string.fbWWW));
                        return true;
                    case R.id.nav_YT:
                        goToWww(getString(R.string.ytWWW));
                        return true;
                    case R.id.nav_WN:
                        goToWww(getString(R.string.wnWWW));
                    default:
                        return false;
                }
            }
        });

    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            super.onBackPressed();
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
    }


    void goToWww(String url){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);

    }
    void goToMap(){
        Intent intent = new Intent(this,MapActivity.class);
        startActivity(intent);

    }

    private void showMapPop() {
        Dialog settingsDialog = new Dialog(this);
        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        settingsDialog.setContentView(getLayoutInflater().inflate(R.layout.map
                , null));
        settingsDialog.show();
        ImageView map = (ImageView)  settingsDialog.findViewById(R.id.map_file);
        Picasso.with(this).load(R.drawable.mapa).into(map);
    }



}
