package com.example.bugtracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FragmentMain extends Fragment {

    private TextView txtName;
    private ImageView imgProfilePic;
    private RecyclerView recViewOptions;
    private OptionsAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main,container,false);

//        initOptions();

        initValues(view);

        recViewOptions = view.findViewById(R.id.recViewOptions);
        adapter = new OptionsAdapter(getActivity());

        recViewOptions.setAdapter(adapter);
        recViewOptions.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.setOptions(Utils.getInstance(getActivity()).getAllOptions(getActivity()));

        return view;
    }
    private void initValues(View view) {
        txtName = view.findViewById(R.id.txtName);
        imgProfilePic = view.findViewById(R.id.imgProfilePic);
//        reViewPlan = findViewById(R.id.recViewPlan);

    }

    private ArrayList<Option> initOptions() {
        ArrayList<Option> options = new ArrayList<>();
        Option allBug = new Option("All Bugs");
        Option myAssignedBugs = new Option("My assigned Bugs");
        Option myReportedBugs = new Option("My reported Bugs");
        Option todaysBugs = new Option("Today's Bugs");
        Option bugsDueTomorrow = new Option("Bugs due tomorrow");
        Option overdueBugs = new Option("Overdue Bugs");

        options.add(allBug);
        options.add(myAssignedBugs);
        options.add(myReportedBugs);
        options.add(todaysBugs);
        options.add(bugsDueTomorrow);
        options.add(overdueBugs);

        return options;
    }
}
