package info.nghiango.sqlite.view;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import info.nghiango.sqlite.R;
import info.nghiango.sqlite.db.UserSqlHelper;
import info.nghiango.sqlite.db.model.User;

public class TestSqliteActivity extends AppCompatActivity {

    private List<User> notesList = new ArrayList<>();
    private UserSqlHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sqlite);

        db = new UserSqlHelper(this);

        notesList.addAll(db.getAllUsers());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_user);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNoteDialog(false, null, -1);
            }
        });
    }



    private void showNoteDialog(final boolean shouldUpdate, final User user, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.user_dialog, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(TestSqliteActivity.this);


        alertDialogBuilderUserInput.setView(view);

        final EditText inputName = view.findViewById(R.id.user_name);
        final EditText inputPass = view.findViewById(R.id.user_pass);
        final EditText inputDisplayName = view.findViewById(R.id.user_display_name);
        final EditText inputType = view.findViewById(R.id.user_type);
        final EditText inputDescription = view.findViewById(R.id.user_description);
        TextView dialogTitle = view.findViewById(R.id.dialog_title);
        dialogTitle.setText(!shouldUpdate ? "Tạo User" : "Cập nhật User");

        if (shouldUpdate && user != null) {
            inputDisplayName.setText(user.getDisplayName());
            inputName.setText(user.getUser());
            inputPass.setText(user.getPass());
            inputType.setText(user.getType());
            inputDescription.setText(user.getDescription());
        }
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(shouldUpdate ? "Cập nhật" : "Lưu", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show toast message when no text is entered
                if (TextUtils.isEmpty(inputName.getText().toString())) {
                    Toast.makeText(TestSqliteActivity.this, "Enter note!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }

                // check if user updating note
                if (shouldUpdate && user != null) {
                    // update note by it's id
                    updateNote(inputNote.getText().toString(), position);
                } else {
                    // create new note
                    createNote(inputNote.getText().toString());
                }
            }
        });
    }
}
