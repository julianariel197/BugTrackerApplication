package com.example.bugtracker;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.example.bugtracker.BugActivity.BUG_ITEM_KEY;

public class BugAdapter extends RecyclerView.Adapter<BugAdapter.ViewHolder> {


    public interface DeleteBug{
        void onDeleteResult(Bug bug);
    }

    private DeleteBug deleteBug;

    private Context context;
    private ArrayList<Bug> bugs = new ArrayList<>();

    public BugAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bug_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.txtBug.setText(bugs.get(position).getBugName());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BugActivity.class);
                intent.putExtra(BUG_ITEM_KEY, bugs.get(position));
//                System.out.println(bugs.get(position).getId());
                context.startActivity(intent);
            }
        });
        holder.imgDeleteBug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle("Deleting...")
                        .setMessage("Are you sure you want to delete this item?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    //instantiate interface to remove routines
                                     deleteBug= (DeleteBug) context;
                                    deleteBug.onDeleteResult(bugs.get(position));

                                } catch (ClassCastException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                builder.create().show();
            }
        });
        }

        @Override
        public int getItemCount () {
            return bugs.size();
        }

        public void setBugs (ArrayList < Bug > bugs) {
            this.bugs = bugs;
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private CardView parent;
            private TextView txtBug;
            private ImageView imgEditBug;
            private ImageView imgDeleteBug;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                parent = itemView.findViewById(R.id.parentBug);
                txtBug = itemView.findViewById(R.id.txtBug);
                imgEditBug = itemView.findViewById(R.id.imgEditBug);
                imgDeleteBug = itemView.findViewById(R.id.imgDelete);
            }
        }
    }
