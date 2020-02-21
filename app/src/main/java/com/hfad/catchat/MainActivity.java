package com.hfad.catchat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Gravity;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this
                ,drawerLayout
                ,toolbar
                ,R.string.nav_open_drawer
                ,R.string.nav_close_drawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        InboxFragment inboxFragment = new InboxFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container,inboxFragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem != null && menuItem.getItemId() == android.R.id.home) {
                    if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                        drawerLayout.closeDrawer(Gravity.RIGHT);
                    }
                    else {
                        drawerLayout.openDrawer(Gravity.RIGHT);
                    }
                }
                Fragment fragment = null;
                Intent i = null;
                switch (menuItem.getItemId()){
                    case R.id.nav_drafts:
                        fragment = new DraftsFragment();
                        break;
                    case R.id.nav_sent:
                        fragment = new SentItemsFragment();
                        break;
                    case R.id.nav_trash:
                        fragment = new TrashFragment();
                        break;
                    case R.id.nav_help:
                        i = new Intent(MainActivity.this,HelpActivity.class);
                        break;
                    case R.id.nav_feedback:
                        i = new Intent(MainActivity.this,FeedbackActivity.class);
                        break;
                        default:
                            fragment = new InboxFragment();
                }
                if (fragment == null){
                    startActivity(i);
                }else{
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_container,fragment);
                    ft.addToBackStack(null);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.commit();
                }
                DrawerLayout drawerLayout1 = (DrawerLayout)findViewById(R.id.drawer_layout);
                drawerLayout1.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else
        super.onBackPressed();
    }
}
