package com.example.bugtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BugActivity extends AppCompatActivity implements AddCommentDialog.AddComment {

    private Boolean initialDisplayStatus = true;
    private Boolean initialDisplaySeverity = true;

    public static final String TAG = "BugActivity";
    public static final String BUG_ITEM_KEY = "incoming_bug";
    private Spinner spinnerSeverity, spinnerStatus;
    private ImageView imgAssignedWorkers;
    private TextView txtBy, txtDueDate, txtBugDescription, txtBugName, txtPostedDate;
    private Calendar calendar = Calendar.getInstance();
    private FloatingActionButton fab;
    private RelativeLayout relLayAddAttachment, relLayAddComments;
    private MaterialToolbar toolbar;
    private CommentAdapter commentAdapter;
    private RecyclerView recyclerViewComments;
    private ImageView firstEmptyStar, secondEmptyStar, thirdEmptyStar, firstFilledStar, secondFilledStar, thirdFilledStar;

    //the navigationVIew item in the xml file has to be outside the relative layout
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Bug incomingBug;


    //initialize calendar for the due date of the bug
    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            txtDueDate.setText("due " + new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));

        }
    };

    @Override
    public void onAddReviewResult(Comment comment) {
        Log.d(TAG, "onAddCommentResult: new comment: " + comment);
        Utils.addComment(BugActivity.this, comment);
        //get all the comments of a bug
        ArrayList<Comment> comments = Utils.getCommentById(this, comment.getBugId());
        if (null != comments) {
            commentAdapter.setComments(comments);
        }
    }

    //create options menu, inflates a menu layout from the menu file
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bugs_menu, menu);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bug);
        initViews();
        initValues();

        commentAdapter = new CommentAdapter();
        Intent intent = getIntent();
        if (intent != null) {
            incomingBug = intent.getParcelableExtra(BUG_ITEM_KEY);
            Log.d(TAG, "onCreate: incomingBug"+ incomingBug);
            if (incomingBug != null) {
                txtBugName.setText(incomingBug.getBugName());
                txtBugDescription.setText(incomingBug.getBugDesc());
                txtDueDate.setText("due " + incomingBug.getDueDate());
                txtPostedDate.setText(incomingBug.getPostDate());
                ArrayList<Comment> comments = Utils.getInstance(BugActivity.this).getCommentsById(this, incomingBug.getId());
                recyclerViewComments.setAdapter(commentAdapter);
                recyclerViewComments.setLayoutManager(new LinearLayoutManager(this));
                if (comments != null) {
                    if (comments.size() > 0) {
                        commentAdapter.setComments(comments);
                    }
                }
                relLayAddComments.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AddCommentDialog addCommentDialog = new AddCommentDialog();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(BUG_ITEM_KEY, incomingBug);
                        addCommentDialog.setArguments(bundle);
                        addCommentDialog.show(getSupportFragmentManager(), "Add comment");

                    }
                });

                handleSeverity(incomingBug.getSeverity());
            }
        }

        initStatusSpinner();
        initSeveritySpinner();

        toolbar = findViewById(R.id.toolbarAllBugs);
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
                        Toast.makeText(BugActivity.this, "Home Selected", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BugActivity.this, AddBugActivity.class);
                startActivity(intent);
            }
        });

        txtDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initDateText();
            }
        });

        relLayAddAttachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO add attachment activity
            }
        });

    }

    private void handleSeverity(int severity) {
        switch (severity) {
            case 0:
                firstEmptyStar.setVisibility(View.VISIBLE);
                firstFilledStar.setVisibility(View.GONE);
                secondEmptyStar.setVisibility(View.VISIBLE);
                secondFilledStar.setVisibility(View.GONE);
                thirdEmptyStar.setVisibility(View.VISIBLE);
                thirdFilledStar.setVisibility(View.GONE);
                break;
            case 1:
                firstEmptyStar.setVisibility(View.GONE);
                firstFilledStar.setVisibility(View.VISIBLE);
                secondEmptyStar.setVisibility(View.VISIBLE);
                secondFilledStar.setVisibility(View.GONE);
                thirdEmptyStar.setVisibility(View.VISIBLE);
                thirdFilledStar.setVisibility(View.GONE);
                break;
            case 2:
                firstEmptyStar.setVisibility(View.GONE);
                firstFilledStar.setVisibility(View.VISIBLE);
                secondEmptyStar.setVisibility(View.GONE);
                secondFilledStar.setVisibility(View.VISIBLE);
                thirdEmptyStar.setVisibility(View.VISIBLE);
                thirdFilledStar.setVisibility(View.GONE);
                break;
            case 3:
                firstEmptyStar.setVisibility(View.GONE);
                firstFilledStar.setVisibility(View.VISIBLE);
                secondEmptyStar.setVisibility(View.GONE);
                secondFilledStar.setVisibility(View.VISIBLE);
                thirdEmptyStar.setVisibility(View.GONE);
                thirdFilledStar.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    private void initViews() {
        navigationView = findViewById(R.id.navigationView);
        drawer = findViewById(R.id.drawer);
    }

    private void initValues() {
        txtDueDate = findViewById(R.id.txtDueDate);
        spinnerSeverity = findViewById(R.id.spinnerSeverity);
        spinnerStatus = findViewById(R.id.spinnerStatus);
        imgAssignedWorkers = findViewById(R.id.imgAssignedWorker);
        txtBy = findViewById(R.id.txtBy);
        fab = findViewById(R.id.fab);
        relLayAddAttachment = findViewById(R.id.relLayAddAttachment);
        txtBugDescription = findViewById(R.id.txtBugDescription);
        txtBugName = findViewById(R.id.txtBugName);
        txtPostedDate = findViewById(R.id.txtPostedDate);
        relLayAddComments = findViewById(R.id.relLayAddComments);
        recyclerViewComments = findViewById(R.id.recViewComments);
        firstEmptyStar = findViewById(R.id.firstEmptyStar);
        firstFilledStar = findViewById(R.id.firstFilledStar);
        secondEmptyStar = findViewById(R.id.secondEmptyStar);
        secondFilledStar = findViewById(R.id.secondFilledStar);
        thirdEmptyStar = findViewById(R.id.thirdEmptyStar);
        thirdFilledStar = findViewById(R.id.thirdFilledStar);


    }

    private void initStatusSpinner() {
        final List<String> status = new ArrayList<>();
        status.add("Status");
        status.add("Open");
        status.add("Trial");
        status.add("Closed");

        ArrayAdapter statusAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, status);
        spinnerStatus.setAdapter(statusAdapter);
        int spinnerPosition = incomingBug.getStatus();
        spinnerStatus.setSelection(spinnerPosition);
        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!initialDisplayStatus) {
                    handleStatus(i);
                } else {
                    initialDisplayStatus = false;
                }
                switch (i) {
                    case 1:
                        Utils.getInstance(BugActivity.this).changeStatus(BugActivity.this, incomingBug.getId(), 1);
                        incomingBug.setStatus(1);
                        handleStatus(1);
                        break;
                    case 2:
                        Utils.getInstance(BugActivity.this).changeStatus(BugActivity.this, incomingBug.getId(), 2);
                        incomingBug.setStatus(2);
                        handleStatus(2);
                        break;
                    case 3:
                        Utils.getInstance(BugActivity.this).changeStatus(BugActivity.this, incomingBug.getId(), 0);
                        incomingBug.setStatus(0);
                        handleStatus(0);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void handleStatus(int bugStatus) {
        switch (bugStatus) {
            case 0:
                txtBugName.setTextColor(Color.RED);
                break;
            case 1:
                txtBugName.setTextColor(Color.GREEN);
                break;
            case 2:
                txtBugName.setTextColor(0xffffff00);
                break;
            default:
                txtBugName.setTextColor(Color.BLACK);
                break;
        }
    }

    private void initSeveritySpinner() {
        final List<String> severity = new ArrayList<>();
        severity.add("Severity");
//        severity.add("Stopped");
        severity.add("Minor");
        severity.add("Mayor");
        severity.add("Critic");

        ArrayAdapter severityAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, severity);
        spinnerSeverity.setAdapter(severityAdapter);
        int spinnerPosition = incomingBug.getSeverity();
        spinnerSeverity.setSelection(spinnerPosition);
        spinnerSeverity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!initialDisplaySeverity) {
                    handleSeverity(i);
                } else {
                    initialDisplaySeverity = false;
                }
                switch (i) {
                    case 1:
                        Utils.getInstance(BugActivity.this).changeSeverity(BugActivity.this, incomingBug.getId(), 1);
                        incomingBug.setSeverity(1);
                        handleSeverity(1);
                        break;
                    case 2:
                        Utils.getInstance(BugActivity.this).changeSeverity(BugActivity.this, incomingBug.getId(), 2);
                        incomingBug.setSeverity(2);
                        handleSeverity(2);
                        break;
                    case 3:
                        Utils.getInstance(BugActivity.this).changeSeverity(BugActivity.this, incomingBug.getId(), 3);
                        incomingBug.setSeverity(3);
                        handleSeverity(3);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initDateText() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();


    }
}