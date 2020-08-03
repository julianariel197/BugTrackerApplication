package com.example.bugtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddBugActivity extends AppCompatActivity {

    private static final String TAG = "AddBugActivity";
    private EditText edtTextTitle, edtTextDesc, edtTextProject;
    private MaterialToolbar materialToolbar;
    private Bug newBug;
    private TextView txtDueDateNewBug;
    private Calendar calendar = Calendar.getInstance();

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_bug_menu, menu);
        return true;
    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            txtDueDateNewBug.setText("due " + new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bug);

        materialToolbar = findViewById(R.id.toolBarAddBug);
        setSupportActionBar(materialToolbar);

        initValues();

        txtDueDateNewBug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initDateText();
            }
        });
//        String bugTitle=edtTextTitle.getText().toString();
//        newBug.setBugName(edtTextTitle.getText().toString());


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.addBugCheck) {
            String bugTitle = edtTextTitle.getText().toString();
            String bugDescription = edtTextDesc.getText().toString();
            String project = edtTextProject.getText().toString();
//            String date=txtDueDateNewBug.toString();
            if (!(TextUtils.isEmpty(bugDescription) || TextUtils.isEmpty(bugTitle) || TextUtils.isEmpty(project))) {
                Bug bug = new Bug(bugTitle, bugDescription, project, "Today", "Tomorrow");
                Log.d(TAG, "onOptionsItemSelected: " + bug);
                Utils.getInstance(AddBugActivity.this).addAllBugs(AddBugActivity.this, bug);
                onBackPressed();
//            Intent intent=new Intent(this,B
//            bugsActivity.class);
////            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
                return true;
            }
            onBackPressed();
            return true;
        }
        return false;
    }

    private void initDateText() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();


    }

    private void initValues() {
        edtTextDesc = findViewById(R.id.edtTxtDescription);
        edtTextTitle = findViewById(R.id.edtTxtTitle);
        edtTextProject = findViewById(R.id.edtTextProject);
        txtDueDateNewBug = findViewById(R.id.txtDueDateNewBug);
    }
}