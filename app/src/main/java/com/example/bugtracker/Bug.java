package com.example.bugtracker;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Bug implements Parcelable {

    protected Bug(Parcel in) {
        id = in.readInt();
        bugName = in.readString();
        bugDesc = in.readString();
        dueDate = in.readString();
        postDate = in.readString();
        severity=in.readInt();
        postedByName=in.readString();
        status=in.readInt();
        project=in.readString();
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public static final Creator<Bug> CREATOR = new Creator<Bug>() {
        @Override
        public Bug createFromParcel(Parcel in) {
            return new Bug(in);
        }

        @Override
        public Bug[] newArray(int size) {
            return new Bug[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(bugName);
        parcel.writeString(bugDesc);
        parcel.writeString(dueDate);
        parcel.writeString(postDate);
        parcel.writeInt(severity);
        parcel.writeString(postedByName);
        parcel.writeInt(status);
        parcel.writeString(project);
    }

    private int id;
    private String bugName;
    private String bugDesc;
    private String dueDate;
    private String postDate;
    private String postedByName;
    private Worker postedBy;
    private int severity;
    private String project;
    private int status;
    private ArrayList<Comment> comments=new ArrayList<>();
    private ArrayList<Worker> contributors=new ArrayList<>();

    public Bug(String bugName, String bugDesc, String project,String dueDate, String postDate) {
        this.id=Utils.getId();
        this.bugName = bugName;
        this.bugDesc = bugDesc;
        this.dueDate = dueDate;
        this.project=project;
        this.postDate = postDate;
        this.severity=0;
        this.status=1;
    }

    @Override
    public String toString() {
        return "Bug{" +
                "id=" + id +
                ", bugName='" + bugName + '\'' +
                ", bugDesc='" + bugDesc + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", postDate='" + postDate + '\'' +
                ", postedByName='" + postedByName + '\'' +
                ", postedBy=" + postedBy +
                ", severity=" + severity +
                ", comments=" + comments +
                ", contributors=" + contributors +
                '}';
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return this.id;
    }

    public String getBugName() {
        return bugName;
    }

    public void setBugName(String bugName) {
        this.bugName = bugName;
    }

    public String getBugDesc() {
        return bugDesc;
    }

    public void setBugDesc(String bugDesc) {
        this.bugDesc = bugDesc;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public ArrayList<Worker> getContributors() {
        return contributors;
    }

    public boolean addContributor(Worker worker){
        for(Worker w:contributors){
            if(w.equals(worker)){
                return false;
            }
        }return contributors.add(worker);
    }

    public void setContributors(ArrayList<Worker> contributors) {
        this.contributors = contributors;
    }
}
