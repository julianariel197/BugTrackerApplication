package com.example.bugtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import static com.example.bugtracker.AllProjectsDialog.ALL_PROJECTS;
import static com.example.bugtracker.AllProjectsDialog.CALLING_ACTIVITY;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";


    /**
     * navigation view is the expandable window on the top right of the header
     * the drawer layout is the whole layout
     * the drawer layout has a frameLayout that is a container for the fragment
     * use FragmentTransaction to get inflate the frameLayout
     */

    private DrawerLayout drawer;
    private MaterialToolbar toolbar;
    private NavigationView navigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        Utils.getInstance(MainActivity.this);

//        Utils.getInstance(this).clearSharedPrefences(this);

//        Utils.getInstance(this).initOptions(this);
        Log.d(TAG, "onCreate: called");
        setSupportActionBar(toolbar);

        //set animation of the navigationView
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Toast.makeText(MainActivity.this, "Home Selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.project:
                        AllProjectsDialog dialog = new AllProjectsDialog();
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList(ALL_PROJECTS, Utils.getProjects(MainActivity.this));
                        bundle.putString(CALLING_ACTIVITY, "main_activity");
                        dialog.setArguments(bundle);
                        dialog.show(getSupportFragmentManager(), "all projects dialog");
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new FragmentMain());
        transaction.commit();
    }

    private void initViews(){
        toolbar =findViewById(R.id.toolbar);
        navigationView=findViewById(R.id.navigationView);
        drawer =findViewById(R.id.drawer);
    }



}