package com.example.bugtracker;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.bugtracker.BugActivity.BUG_ITEM_KEY;

public class AddCommentDialog extends DialogFragment {

    public interface AddComment {
        void onAddReviewResult(Comment comment);
    }

    private AddComment addComment;
    private TextView txtBugName, txtWarning;
    private EditText edtTxtUserName;
    private EditText edtTxtComment;
    private Button btnAddComment;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_comment, null);
        initViews(view);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view);

        Bundle bundle = getArguments();
        if (null != bundle) {
            final Bug bug = bundle.getParcelable(BUG_ITEM_KEY);
            if (null != bug) {
                txtBugName.setText(bug.getBugName());
                btnAddComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String userName = edtTxtUserName.getText().toString();
                        String review = edtTxtComment.getText().toString();
//                        String date = getCurrentDate();
                        if (userName.equals("") || review.equals("")) {
                            txtWarning.setText("Fill all the blanks");
                            txtWarning.setVisibility(View.VISIBLE);
                        } else {
                            txtWarning.setVisibility(View.GONE);
                            try {
                                addComment = (AddComment) getActivity();
                                //implement callback interface to send the review back to the activity
                                addComment.onAddReviewResult(new Comment(bug.getId(), userName, review, "today"));

                                dismiss();
                            } catch (ClassCastException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        }
        return builder.create();
    }

    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-YYYY");
        return sdf.format(calendar.getTime());
    }

    private void initViews(View view) {
        txtBugName = view.findViewById(R.id.txtBugNameComment);
        txtWarning = view.findViewById(R.id.txtWarning);
        edtTxtComment = view.findViewById(R.id.edtTxtComment);
        edtTxtUserName = view.findViewById(R.id.edtTxtUserName);
        btnAddComment = view.findViewById(R.id.btnAddComment);


    }
}
