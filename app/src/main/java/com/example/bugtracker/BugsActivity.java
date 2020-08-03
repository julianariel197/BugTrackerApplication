package com.example.bugtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class BugsActivity extends AppCompatActivity implements BugAdapter.DeleteBug {
    public static final String TAG = "BugsActivity";
    public static final String OPTION_KEY = "option";

    private RecyclerView recViewBugs;
    private Button btnAddBugs;
    private TextView txtProjectName;
    private ListView listProjects;
    private RelativeLayout relLayNoBug, relLayBugs, relLayProjects, relLayExpanProjects;
    private BugAdapter bugAdapter;
    private Boolean visExpan = false;
    private Boolean visNoBug = true;
    private FloatingActionButton fab;
    private MaterialToolbar toolbar;
    private int option;


    /**
     * onCreateOptions menu is for setting the icons in the menu(header bar)
     * first create a material toolbar in the xml file
     * then initialize it in the java file and call setSupportActionBar() to apply it
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bugs_menu, menu);
        return true;
    }

    @Override
    public void onDeleteResult(Bug bug) {
        switch (option) {
            case 1:
                if (Utils.getInstance(this).removeAllBugs(this, bug)) {
                    Toast.makeText(this, "Bug removed", Toast.LENGTH_SHORT).show();
                    bugAdapter.setBugs(Utils.getAllBugs(this));
                }
                break;
            case 2:
                if (Utils.getInstance(this).removeMyAssignedBugs(this, bug)) {
                    Toast.makeText(this, "Bug removed", Toast.LENGTH_SHORT).show();
                    bugAdapter.setBugs(Utils.getAllBugs(this));
                }
                break;
            case 3:
                if (Utils.getInstance(this).removeMyReportedBugs(this, bug)) {
                    Toast.makeText(this, "Bug removed", Toast.LENGTH_SHORT).show();
                    bugAdapter.setBugs(Utils.getAllBugs(this));
                }
                break;
            case 4:
                if (Utils.getInstance(this).removeTodayBug(this, bug)) {
                    Toast.makeText(this, "Bug removed", Toast.LENGTH_SHORT).show();
                    bugAdapter.setBugs(Utils.getAllBugs(this));
                }
                break;
            case 5:
                if (Utils.getInstance(this).removeBugDueTomorrow(this, bug)) {
                    Toast.makeText(this, "Bug removed", Toast.LENGTH_SHORT).show();
                    bugAdapter.setBugs(Utils.getAllBugs(this));
                }
                break;
            case 6:
                if (Utils.getInstance(this).removeOverdueBug(this, bug)) {
                    Toast.makeText(this, "Bug removed", Toast.LENGTH_SHORT).show();
                    bugAdapter.setBugs(Utils.getAllBugs(this));
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bugs);
        toolbar = findViewById(R.id.toolbarAllBugs);

        setSupportActionBar(toolbar);

        initValues();

        relLayProjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!visExpan) {
                    relLayExpanProjects.setVisibility(View.VISIBLE);
                    relLayNoBug.setVisibility(View.GONE);
                } else {
                    relLayExpanProjects.setVisibility(View.GONE);
                    relLayNoBug.setVisibility(View.VISIBLE);

                }


            }
        });

        txtProjectName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relLayExpanProjects.setVisibility(View.GONE);
                if (visNoBug) {
                    relLayNoBug.setVisibility(View.VISIBLE);
                }
            }
        });

        btnAddBugs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(BugsActivity.this, AddBugActivity.class);
                startActivity(intent1);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BugsActivity.this, AddBugActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.itemOrderBy:
                Toast.makeText(this, "sort by clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.itemSearch:
                Toast.makeText(this, "search Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BugsActivity.this, SearchActivity.class);
//                 intent.setFlags()
                startActivity(intent);
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bugAdapter = new BugAdapter(this);
        final Intent intent = getIntent();
        if (intent != null) {
            option = intent.getIntExtra(OPTION_KEY, -1);
            System.out.println("Value of option is " + option);
            if (option != -1) {
                ArrayList<Bug> bugs = Utils.getInstance(this).getBugsByOption(this, option);
                recViewBugs.setAdapter(bugAdapter);
                recViewBugs.setLayoutManager(new LinearLayoutManager(this));
                if (bugs != null) {
                    if (bugs.size() > 0) {
                        Comparator<Bug> bugComparator = new Comparator<Bug>() {
                            @Override
                            public int compare(Bug bug1, Bug bug2) {
                                return bug1.getId() - bug2.getId();
                            }
                        };
                        Comparator<Bug> reverseComparator = Collections.reverseOrder(bugComparator);
                        Collections.sort(bugs, reverseComparator);
                        bugAdapter.setBugs(bugs);
                        visNoBug = false;
                        relLayBugs.setVisibility(View.VISIBLE);
                        relLayNoBug.setVisibility(View.GONE);
                    } else {
                        visNoBug = true;
                        relLayNoBug.setVisibility(View.VISIBLE);

                    }
                } else {
                    Toast.makeText(this, "the list is empty", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void initValues() {
        fab = findViewById(R.id.fab);
        recViewBugs = findViewById(R.id.recViewBugs);
        btnAddBugs = findViewById(R.id.btnAddBug);
        txtProjectName = findViewById(R.id.txtProjectName);
        listProjects = findViewById(R.id.projectList);
        relLayBugs = findViewById(R.id.relLayBugs);
        relLayNoBug = findViewById(R.id.relLayNoBug);
        relLayProjects = findViewById(R.id.relLayProjects);
        relLayExpanProjects = findViewById(R.id.relLayExpanProject);

    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        Intent intent = new Intent(this, TrackUserTime.class);
//        bindService(intent, connection, BIND_AUTO_CREATE);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//
//        if (isBound) {
//            unbindService(connection);
//        }
//    }
}