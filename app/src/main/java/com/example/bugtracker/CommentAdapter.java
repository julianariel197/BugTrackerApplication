package com.example.bugtracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private ArrayList<Comment> comments = new ArrayList<>();

    public CommentAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtComment.setText(comments.get(position).getText());
        holder.txtDate.setText(comments.get(position).getDate());
        holder.txtUserName.setText(comments.get(position).getUserName());

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtUserName, txtComment, txtDate;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtUserName = itemView.findViewById(R.id.txtUserNameComment);
            txtComment = itemView.findViewById(R.id.txtComment);
            txtDate = itemView.findViewById(R.id.txtDateCommented);

        }
    }
}
