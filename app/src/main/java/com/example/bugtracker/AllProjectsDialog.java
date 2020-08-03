package com.example.bugtracker;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class AllProjectsDialog extends DialogFragment {

    public interface GetProject{
        void onGetProjectResult(String project);
    }

    private GetProject getProject;

    public static final String ALL_PROJECTS = "projects";
    public static final String CALLING_ACTIVITY = "activity";


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_all_projects, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view);

        Bundle bundle = getArguments();
        if (null != bundle) {
            final String callingActivity = bundle.getString(CALLING_ACTIVITY);
            final ArrayList<String> projects = bundle.getStringArrayList(ALL_PROJECTS);
            if (null != projects) {
                ListView listView = view.findViewById(R.id.listViewProjects);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_1,
                        projects);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (callingActivity) {
                            case "main_activity":
                                Intent intent = new Intent(getActivity(), SearchActivity.class);
                                intent.putExtra("project", projects.get(position));
//                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                getActivity().startActivity(intent);
                                dismiss();
                                break;
                            case "search_activity":
                                try {
                                    getProject = (GetProject) getActivity();
                                    getProject.onGetProjectResult(projects.get(position));
                                    dismiss();
                                }catch (ClassCastException e) {
                                    e.printStackTrace();
                                }
                                break;
                            default:
                                break;
                        }
                    }
                });
            }
        }

        return builder.create();
    }

}
