package com.example.bugtracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.example.bugtracker.BugsActivity.OPTION_KEY;

public class OptionsAdapter extends RecyclerView.Adapter<OptionsAdapter.ViewHolder> {

    private ArrayList<Option> options = new ArrayList<>();
    private Context context;
    public static final String TAG = "BugAdapter";

    public OptionsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bug_options_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.txtOption.setText(options.get(position).getOptionName());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BugsActivity.class);
                intent.putExtra(OPTION_KEY, options.get(position).getId());
                System.out.println("sending "+ options.get(position).getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    public void setOptions(ArrayList<Option> options) {
        this.options = options;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView parent;
        private TextView txtOption;
        private ImageView imgBug;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.parent);
            txtOption = itemView.findViewById(R.id.txtOption);
            imgBug = itemView.findViewById(R.id.imgBug);
        }
    }
}
