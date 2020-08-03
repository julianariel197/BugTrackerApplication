//package com.example.bugtracker;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ListView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//
//import java.util.ArrayList;
//
//public class FragmentAllBugs extends Fragment {
//    public static final String TAG = "BugsActivity";
//    public static final String OPTION_KEY = "option";
//
//
//    private RecyclerView recViewBugs;
//    private Button btnAddBugs;
//    private TextView txtProjectName;
//    private ListView listProjects;
//    private RelativeLayout relLayNoBug, relLayBugs, relLayProjects, relLayExpanProjects;
//    private BugAdapter bugAdapter;
//    private Boolean visExpan = false;
//    private Boolean visNoBug = true;
//    private FloatingActionButton fab;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view=inflater.inflate(R.layout.fragment_all_bugs,container,false);
//
//        initValues(view);
//
//        bugAdapter = new BugAdapter(getActivity());
//        recViewBugs.setAdapter(bugAdapter);
//        recViewBugs.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//        Intent intent = getIntent();
//        if (intent != null) {
//            final int option = intent.getIntExtra(OPTION_KEY, -1);
//            if (option != -1) {
//                ArrayList<Bug> bugs = Utils.getInstance(this).getBugsByOption(option);
//                bugAdapter.setBugs(bugs);
//                if (bugs.size() > 0) {
//                    visNoBug = false;
//                    relLayBugs.setVisibility(View.VISIBLE);
//                    relLayNoBug.setVisibility(View.GONE);
//
//
//                } else {
//                    visNoBug = true;
//                    relLayNoBug.setVisibility(View.VISIBLE);
//
//                }
//            }
//        }
//        relLayProjects.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!visExpan) {
//                    relLayExpanProjects.setVisibility(View.VISIBLE);
//                    relLayNoBug.setVisibility(View.GONE);
//                } else {
//                    relLayExpanProjects.setVisibility(View.GONE);
//                    relLayNoBug.setVisibility(View.VISIBLE);
//
//                }
//
//
//            }
//        });
//
//        txtProjectName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                relLayExpanProjects.setVisibility(View.GONE);
//                if(visNoBug){
//                    relLayNoBug.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//
//        btnAddBugs.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent1=new Intent(getActivity(),AddBugActivity.class);
//                startActivity(intent1);
//            }
//        });
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(getActivity(),AddBugActivity.class);
//                startActivity(intent);
//            }
//        });
//        return view;
//    }
//
//    private void initValues(View view) {
//        fab=view.findViewById(R.id.fab);
//        recViewBugs = view.findViewById(R.id.recViewBugs);
//        btnAddBugs = view.findViewById(R.id.btnAddBug);
//        txtProjectName = view.findViewById(R.id.txtProjectName);
//        listProjects = view.findViewById(R.id.projectList);
//        relLayBugs = view.findViewById(R.id.relLayBugs);
//        relLayNoBug = view.findViewById(R.id.relLayNoBug);
//        relLayProjects = view.findViewById(R.id.relLayProjects);
//        relLayExpanProjects = view.findViewById(R.id.relLayExpanProject);
//
//    }
//}
