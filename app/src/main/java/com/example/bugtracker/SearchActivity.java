package com.example.bugtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import static com.example.bugtracker.AllProjectsDialog.ALL_PROJECTS;
import static com.example.bugtracker.AllProjectsDialog.CALLING_ACTIVITY;

public class SearchActivity extends AppCompatActivity implements AllProjectsDialog.GetProject {
    public static final String TAG="SearchActivity";

    private MaterialToolbar toolbar;
    private EditText searchBox;
    private ImageView btnSearch;
    private TextView openStatus, trialStatus, closedStatus, txtAllProjects,txtNoBugsFound;
    private RecyclerView recyclerView;
    //    private NavigationView navigationView;
    private BugAdapter adapter;
    private Spinner spinnerProjects;
//    private DrawerLayout drawer;


    @Override
    public void onGetProjectResult(String project) {
        ArrayList<Bug> bugs = Utils.getBugsByProject(this, project);
        if (null != bugs) {
            adapter.setBugs(bugs);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
//        initViews();
        initValues();
        initProjectSpinner();

        toolbar = findViewById(R.id.toolbarSearch);
        setSupportActionBar(toolbar);

        adapter = new BugAdapter(SearchActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        if (null != intent) {
            String category = intent.getStringExtra("project");
            if (null != category) {
                ArrayList<Bug> items = Utils.getBugsByProject(this, category);
                if (null != items) {
                    adapter.setBugs(items);
                }
            }
        }


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initSearch();
            }
        });


        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                initSearch();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        showByStatus();

        txtAllProjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllProjectsDialog dialog=new AllProjectsDialog();
                Bundle bundle=new Bundle();
                bundle.putStringArrayList(ALL_PROJECTS,Utils.getInstance(SearchActivity.this).getProjects(SearchActivity.this));
                bundle.putString(CALLING_ACTIVITY,"search_activity");
                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(),"All Project Dialog");
            }
        });
    }

    private void initProjectSpinner() {
        final List<String> allProjects = Utils.getInstance(SearchActivity.this).getProjects(SearchActivity.this);
        ArrayAdapter projectsAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, allProjects);
        spinnerProjects.setAdapter(projectsAdapter);
        spinnerProjects.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = spinnerProjects.getSelectedItem().toString();
                ArrayList<Bug> bugsByProject = Utils.getInstance(SearchActivity.this).getBugsByProject(SearchActivity.this, text);
                Log.d(TAG, "onItemSelected: itemSelected"+ text);
                if (bugsByProject != null) {
                    adapter.setBugs(bugsByProject);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void showByStatus() {
        openStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Bug> openBugs=Utils.getInstance(SearchActivity.this).getBugsByStatus(SearchActivity.this,1);
                if(openBugs!=null){
                    adapter.setBugs(openBugs);
                    if(openBugs.size()>0) {
                        txtNoBugsFound.setVisibility(View.GONE);
                    }else{
                        txtNoBugsFound.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        trialStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Bug> openBugs=Utils.getInstance(SearchActivity.this).getBugsByStatus(SearchActivity.this,2);
                if(openBugs!=null){
                    adapter.setBugs(openBugs);
                    if(openBugs.size()>0) {
                        txtNoBugsFound.setVisibility(View.GONE);
                    }else{
                        txtNoBugsFound.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        closedStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Bug> openBugs=Utils.getInstance(SearchActivity.this).getBugsByStatus(SearchActivity.this,3);
                if(openBugs!=null){
                    adapter.setBugs(openBugs);
                    if(openBugs.size()>0) {
                        txtNoBugsFound.setVisibility(View.GONE);
                    }else{
                        txtNoBugsFound.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    private void initSearch() {
        if (!searchBox.getText().toString().equals("")) {
            String text = searchBox.getText().toString();
            ArrayList<Bug> searchBugs = Utils.getInstance(SearchActivity.this).searchForBugs(SearchActivity.this, text);
            if (searchBugs != null) {
                adapter.setBugs(searchBugs);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.addBugCheck) {
            onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_header, menu);
        return true;
    }


    private void initValues() {
        toolbar = findViewById(R.id.toolbar);
        searchBox = findViewById(R.id.searchBox);
        btnSearch = findViewById(R.id.btnSearch);
        openStatus = findViewById(R.id.txtFirstCat);
        trialStatus = findViewById(R.id.txtSecondCat);
        closedStatus = findViewById(R.id.txtThirdCat);
        txtAllProjects = findViewById(R.id.txtAllCategories);
        recyclerView = findViewById(R.id.recyclerView);
        txtNoBugsFound=findViewById(R.id.txtNoBugsFound);
        spinnerProjects=findViewById(R.id.spinnerProject);
    }
//
//    private void initViews() {
////        navigationView = findViewById(R.id.navigationView);
//        drawer = findViewById(R.id.drawer);
//    }
}