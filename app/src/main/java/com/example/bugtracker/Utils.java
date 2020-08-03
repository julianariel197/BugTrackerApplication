package com.example.bugtracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Path;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {
    public static final String TAG = "Utils";
    private static Utils instance;


    private static Gson gson = new Gson();
    private static Type bugListType = new TypeToken<ArrayList<Bug>>() {
    }.getType();
    private static Type optionListType = new TypeToken<ArrayList<Option>>() {
    }.getType();

    private static int ID = 0;
    private static int optionID = 0;

    private SharedPreferences sharedPreferences;
    public static final String DB_NAME = "fake_database";
    private static final String OPTIONS_KEY = "options";
    private static final String ALL_BUGS_KEY = "all_bugs";
    private static final String MY_ASSIGNED_BUGS_KEY = "my_assigned_bugs";
    private static final String MY_REPORTED_BUGS_KEY = "my_reported_bugs";
    private static final String TODAYS_BUGS_KEY = "todays_bugs";
    private static final String BUGS_DUE_TOMORROW_KEY = "bugs_due_tomorrow";
    private static final String OVERDUE_BUGS_KEY = "overdue_bugs";


    private Utils(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, context.MODE_PRIVATE);
        ArrayList<Bug> bugs = gson.fromJson(sharedPreferences.getString(ALL_BUGS_KEY, null), bugListType);
        ArrayList<Option> options = gson.fromJson(sharedPreferences.getString(OPTIONS_KEY, null), optionListType);


        if (bugs == null) {
            initData(context);
        }

        if (options == null) {
            initOptions(context);
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (getMyAssignedBugs(context) == null) {
            editor.putString(MY_ASSIGNED_BUGS_KEY, gson.toJson(new ArrayList<Bug>()));
            editor.commit();
        }

        if (getMyReportedBugs(context) == null) {
            editor.putString(MY_REPORTED_BUGS_KEY, gson.toJson(new ArrayList<Bug>()));
            editor.commit();
        }
        if (getMyTodaysBugs(context) == null) {
            editor.putString(TODAYS_BUGS_KEY, gson.toJson(new ArrayList<Bug>()));
            editor.commit();
        }
        if (getBugsDueTomorrow(context) == null) {
            editor.putString(BUGS_DUE_TOMORROW_KEY, gson.toJson(new ArrayList<Bug>()));
            editor.commit();
        }
        if (getOverdueBugs(context) == null) {
            editor.putString(OVERDUE_BUGS_KEY, gson.toJson(new ArrayList<Bug>()));
            editor.commit();
        }


    }


    public void clearSharedPrefences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    //
    public static void initOptions(Context context) {
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

        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String text = gson.toJson(options);
        editor.putString(OPTIONS_KEY, text);
        editor.commit();
    }


    private void initData(Context context) {
        ArrayList<Bug> bugs = new ArrayList<>();
//        ArrayList<Option> options;

        Bug fixUtils = new Bug("Fix Utils", "add more elements to util class","Java", "July 26", "July 25");
        Bug fixAddBug = new Bug("Fix add bug", "edit add bug to database","Android", "July 26", "July 25");
        Bug fixTestBug = new Bug("Fix test bug", "edit add bug to database", "Java","July 26", "July 25");
        bugs.add(fixTestBug);
        bugs.add(fixAddBug);
        bugs.add(fixUtils);
//        for(Bug b:bugs){
//            Log.d(TAG, "initData: bug id= "+ b.getId());
//            System.out.println("adding fix Bug with id "+ b.getId());
//        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, context.MODE_PRIVATE);


        SharedPreferences.Editor editor = sharedPreferences.edit();

        String text = gson.toJson(bugs);
        editor.putString(ALL_BUGS_KEY, text);
        editor.apply();

    }

    public static Utils getInstance(Context context) {
        if (instance == null) {
            instance = new Utils(context);
        }
        return instance;
    }

    public static ArrayList<Option> getAllOptions(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, context.MODE_PRIVATE);
        ArrayList<Option> options = gson.fromJson(sharedPreferences.getString(OPTIONS_KEY, null), optionListType);
        return options;
    }

    public static ArrayList<Bug> getAllBugs(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, context.MODE_PRIVATE);
        ArrayList<Bug> bugs = gson.fromJson(sharedPreferences.getString(ALL_BUGS_KEY, null), bugListType);
        return bugs;

    }

    public static ArrayList<Bug> getMyAssignedBugs(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, context.MODE_PRIVATE);

        ArrayList<Bug> bugs = gson.fromJson(sharedPreferences.getString(MY_ASSIGNED_BUGS_KEY, null), bugListType);
        return bugs;
    }

    public static ArrayList<Bug> getMyTodaysBugs(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, context.MODE_PRIVATE);
        ArrayList<Bug> bugs = gson.fromJson(sharedPreferences.getString(TODAYS_BUGS_KEY, null), bugListType);
        return bugs;
    }

    public static ArrayList<Bug> getBugsDueTomorrow(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, context.MODE_PRIVATE);
        ArrayList<Bug> bugs = gson.fromJson(sharedPreferences.getString(BUGS_DUE_TOMORROW_KEY, null), bugListType);
        return bugs;
    }

    public static ArrayList<Bug> getOverdueBugs(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, context.MODE_PRIVATE);
        ArrayList<Bug> bugs = gson.fromJson(sharedPreferences.getString(OVERDUE_BUGS_KEY, null), bugListType);
        return bugs;
    }

    public static ArrayList<Bug> getMyReportedBugs(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, context.MODE_PRIVATE);
        ArrayList<Bug> bugs = gson.fromJson(sharedPreferences.getString(MY_REPORTED_BUGS_KEY, null), bugListType);
        return bugs;
    }

    public boolean addAllBugs(Context context, Bug bug) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, context.MODE_PRIVATE);
        ArrayList<Bug> bugs = gson.fromJson(sharedPreferences.getString(ALL_BUGS_KEY, null), bugListType);
        if (bugs == null) {
            bugs = new ArrayList<>();
        }
        if (bugs.add(bug)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(ALL_BUGS_KEY);
            editor.putString(ALL_BUGS_KEY, gson.toJson(bugs));
            editor.commit();
            return true;
        }
        return false;
    }

    public boolean addMyAssignedBugs(Context context, Bug bug) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, context.MODE_PRIVATE);
        ArrayList<Bug> bugs = gson.fromJson(sharedPreferences.getString(MY_ASSIGNED_BUGS_KEY, null), bugListType);
        if (bugs == null) {
            bugs = new ArrayList<>();
        }
        if (bugs.add(bug)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(MY_ASSIGNED_BUGS_KEY);
            editor.putString(MY_ASSIGNED_BUGS_KEY, gson.toJson(bugs));
            editor.commit();
            return true;
        }
        return false;
    }

    public boolean addMyReportedBugs(Context context, Bug bug) {
        ArrayList<Bug> bugs = getMyReportedBugs(context);
        if (bugs != null) {
            if (bugs.add(bug)) {
                SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(MY_REPORTED_BUGS_KEY);
                editor.putString(MY_REPORTED_BUGS_KEY, gson.toJson(bugs));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean addTodaysBugs(Context context, Bug bug) {
        ArrayList<Bug> bugs = getMyTodaysBugs(context);
        if (bugs != null) {
            if (bugs.add(bug)) {
                SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(TODAYS_BUGS_KEY);
                editor.putString(TODAYS_BUGS_KEY, gson.toJson(bugs));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean addBugsDueTomorrow(Context context, Bug bug) {
        ArrayList<Bug> bugs = getBugsDueTomorrow(context);
        if (bugs != null) {
            if (bugs.add(bug)) {
                SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(BUGS_DUE_TOMORROW_KEY);
                editor.putString(BUGS_DUE_TOMORROW_KEY, gson.toJson(bugs));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean addOverdueBug(Context context, Bug bug) {
        ArrayList<Bug> bugs = getOverdueBugs(context);
        if (bugs != null) {
            if (bugs.add(bug)) {
                SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(OVERDUE_BUGS_KEY);
                editor.putString(OVERDUE_BUGS_KEY, gson.toJson(bugs));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean removeAllBugs(Context context, Bug bug) {
        ArrayList<Bug> bugs = getAllBugs(context);
        if (bugs != null) {
            for (Bug b : bugs) {
                if (b.getId() == bug.getId()) {
                    if (bugs.remove(b)) {
                        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(ALL_BUGS_KEY);
                        editor.putString(ALL_BUGS_KEY, gson.toJson(bugs));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean removeMyAssignedBugs(Context context, Bug bug) {
        ArrayList<Bug> bugs = getMyAssignedBugs(context);
        if (bugs != null) {
            for (Bug b : bugs) {
                if (b.getId() == bug.getId()) {
                    if (bugs.remove(b)) {
                        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(MY_ASSIGNED_BUGS_KEY);
                        editor.putString(MY_ASSIGNED_BUGS_KEY, gson.toJson(bugs));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean removeMyReportedBugs(Context context, Bug bug) {
        ArrayList<Bug> bugs = getMyReportedBugs(context);
        if (bugs != null) {
            for (Bug b : bugs) {
                if (b.getId() == bug.getId()) {
                    if (bugs.remove(b)) {
                        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(MY_REPORTED_BUGS_KEY);
                        editor.putString(MY_REPORTED_BUGS_KEY, gson.toJson(bugs));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean removeTodayBug(Context context, Bug bug) {
        ArrayList<Bug> bugs = getMyAssignedBugs(context);
        if (bugs != null) {
            for (Bug b : bugs) {
                if (b.getId() == bug.getId()) {
                    if (bugs.remove(b)) {
                        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(TODAYS_BUGS_KEY);
                        editor.putString(TODAYS_BUGS_KEY, gson.toJson(bugs));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean removeBugDueTomorrow(Context context, Bug bug) {
        ArrayList<Bug> bugs = getMyAssignedBugs(context);
        if (bugs != null) {
            for (Bug b : bugs) {
                if (b.getId() == bug.getId()) {
                    if (bugs.remove(b)) {
                        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(BUGS_DUE_TOMORROW_KEY);
                        editor.putString(BUGS_DUE_TOMORROW_KEY, gson.toJson(bugs));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean removeOverdueBug(Context context, Bug bug) {
        ArrayList<Bug> bugs = getMyAssignedBugs(context);
        if (bugs != null) {
            for (Bug b : bugs) {
                if (b.getId() == bug.getId()) {
                    if (bugs.remove(b)) {
                        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(OVERDUE_BUGS_KEY);
                        editor.putString(OVERDUE_BUGS_KEY, gson.toJson(bugs));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public Bug returnBugById(Context context, int id) {
        ArrayList<Bug> bugs = getAllBugs(context);
        if (bugs != null) {
            for (Bug b : bugs) {
                if (b.getId() == id) {
                    return b;
                }
            }
        }
        return null;
    }

    public static void addComment(Context context, Comment comment) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ArrayList<Bug> allBugs = getAllBugs(context);
        if (null != allBugs) {
            ArrayList<Bug> newBugs = new ArrayList<>();
            for (Bug i : allBugs) {
                if (i.getId() == comment.getBugId()) {
                    ArrayList<Comment> comments = i.getComments();
                    comments.add(comment);
                    i.setComments(comments);
                    newBugs.add(i);
                } else {
                    newBugs.add(i);
                }
            }
            editor.remove(ALL_BUGS_KEY);
            editor.putString(ALL_BUGS_KEY, gson.toJson(newBugs));
            editor.commit();
        }
    }

    public static ArrayList<Comment> getCommentById(Context context, int bugId) {
        ArrayList<Bug> allBugs = getAllBugs(context);
        if (null != allBugs) {
            for (Bug i : allBugs) {
                if (i.getId() == bugId) {
                    ArrayList<Comment> reviews = i.getComments();
                    return reviews;
                }
            }
        }
        return null;
    }

    public ArrayList<Comment> getCommentsById(Context context, int bugId) {
        ArrayList<Bug> bugs = getAllBugs(context);
        if (bugs != null) {
            for (Bug b : bugs) {
                if (b.getId() == bugId) {
                    ArrayList<Comment> comments = b.getComments();
                    return comments;
                }
            }
        }
        return null;
    }

    public ArrayList<Bug> getBugsByOption(Context context, int option) {
        switch (option) {
            case 1:
                return getAllBugs(context);
            case 2:
                return getMyAssignedBugs(context);
            case 3:
                return getMyReportedBugs(context);
            case 4:
                return getMyTodaysBugs(context);
            case 5:
                return getBugsDueTomorrow(context);
            case 6:
                return getOverdueBugs(context);
            default:
                return null;
        }
    }

    public static void changeStatus(Context context, int bugId, int newStatus) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ArrayList<Bug> allBugs = gson.fromJson(sharedPreferences.getString(ALL_BUGS_KEY, null), bugListType);
        if (null != allBugs) {
            ArrayList<Bug> newItems = new ArrayList<>();
            for (Bug i : allBugs) {
                if (i.getId() == bugId) {
                    i.setStatus(newStatus);
                    newItems.add(i);
                } else {
                    newItems.add(i);
                }
            }

            editor.remove(ALL_BUGS_KEY);
            editor.putString(ALL_BUGS_KEY, gson.toJson(newItems));
            editor.commit();
        }
    }

    public static void changeSeverity(Context context, int bugId, int newSeverity) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ArrayList<Bug> allItems = gson.fromJson(sharedPreferences.getString(ALL_BUGS_KEY, null), bugListType);
        if (null != allItems) {
            ArrayList<Bug> newItems = new ArrayList<>();
            for (Bug i : allItems) {
                if (i.getId() == bugId) {
                    i.setSeverity(newSeverity);
                    newItems.add(i);
                } else {
                    newItems.add(i);
                }
            }

            editor.remove(ALL_BUGS_KEY);
            editor.putString(ALL_BUGS_KEY, gson.toJson(newItems));
            editor.commit();
        }
    }

    public static ArrayList<Bug> searchForBugs(Context context, String text) {
        ArrayList<Bug> allBugs = getAllBugs(context);
        if (null != allBugs) {
            ArrayList<Bug> bugs = new ArrayList<>();
            for (Bug bug : allBugs) {
                if (bug.getBugName().equalsIgnoreCase(text)) {
                    bugs.add(bug);
                }

                String[] names = bug.getBugName().split(" ");
                for (int i = 0; i < names.length; i++) {
                    if (text.equalsIgnoreCase(names[i])) {
                        //to avoid duplicity
                        boolean doesExist = false;
//TODO use regex to optimize the search
                        for (Bug j : bugs) {
                            if (j.getId() == bug.getId()) {
                                doesExist = true;
                            }
                        }
                        if (!doesExist) {
                            bugs.add(bug);
                        }
                    }
                }
            }
            return bugs;
        }
        return null;
    }

    public static ArrayList<Bug> getBugsByStatus(Context context, int status) {
        ArrayList<Bug> allBugs = getAllBugs(context);
        if (null != allBugs) {
            ArrayList<Bug> bugs = new ArrayList<>();
            for (Bug bug : allBugs) {
                if (bug.getStatus()==status) {
                    bugs.add(bug);
                }
            }

            return bugs;
        }

        return null;
    }
    public static ArrayList<String> getProjects(Context context) {
        ArrayList<Bug> allBugs = getAllBugs(context);
        if (null != allBugs) {
            ArrayList<String> projects = new ArrayList<>();
            for (Bug bug : allBugs) {
                boolean doesExist = false;
                for (String s : projects) {
                    if (bug.getProject().equals(s)) {
                        doesExist = true;
                    }
                }
                if (!doesExist) {
                    projects.add(bug.getProject());
                }
            }
            return projects;
        }
        return null;
    }

    public static ArrayList<Bug> getBugsByProject(Context context, String project) {
        ArrayList<Bug> allBugs = getAllBugs(context);
        if (null != allBugs) {
            ArrayList<Bug> bugs = new ArrayList<>();
            for (Bug bug : allBugs) {
                if (bug.getProject().equalsIgnoreCase(project)) {
                    bugs.add(bug);
                }
            }
            return bugs;
        }

        return null;
    }

    public static ArrayList<Integer> getStatus(Context context) {
        ArrayList<Bug> allBugs = getAllBugs(context);
        if (null != allBugs) {
            ArrayList<Integer> status = new ArrayList<>();
            for (Bug bug : allBugs) {
                boolean doesExist = false;
                for (Integer i : status) {
                    if (bug.getStatus()==i) {
                        doesExist = true;
                    }
                }

                if (!doesExist) {
                    status.add(bug.getStatus());
                }
            }

            return status;
        }

        return null;
    }

    public static int getId() {
       ID++;
       return ID;
    }

    public static int getOptionId() {
        optionID++;
        return optionID;
    }
}
