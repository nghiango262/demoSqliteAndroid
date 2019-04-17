package info.nghiango.sqlite.view;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import info.nghiango.sqlite.utils.MyDividerItemDecoration;
import info.nghiango.sqlite.utils.RecyclerTouchListener;

public class TestSqliteActivity extends AppCompatActivity {

    private List<User> usersList = new ArrayList<>();
    private UserSqlHelper db;
    private UsersAdapter mAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sqlite);
        recyclerView = findViewById(R.id.recycler_view);

        db = new UserSqlHelper(this);

        usersList.addAll(db.getAllUsers());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_user);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNoteDialog(false, null, -1);
            }
        });

        mAdapter = new UsersAdapter(this, usersList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));

    }

    /**
     * Opens dialog with Edit - Delete options
     * Edit - 0
     * Delete - 0
     */
    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Edit", "Delete"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    showNoteDialog(true, usersList.get(position), position);
                } else {
                    deleteUser(position);
                }
            }
        });
        builder.show();
    }

    /**
     * Deleting User from SQLite and removing the
     * item from the list by its position
     */
    private void deleteUser(int position) {
        // deleting the note from db
        db.deleteUser(usersList.get(position));

        // removing the note from the list
        usersList.remove(position);
        mAdapter.notifyItemRemoved(position);

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
                    //updateNote(inputNote.getText().toString(), position);
                    Toast.makeText(getBaseContext(), user.getDisplayName(), Toast.LENGTH_SHORT).show();
                } else {
                    // create new note
                    createUser(inputName.getText().toString(), inputPass.getText().toString(),inputDisplayName.getText().toString(), inputType.getText().toString(), inputDescription.getText().toString() );
                }
            }
        });
    }

    /**
     * Inserting new User in db
     * and refreshing the list
     */
    private void createUser(String user, String pass, String displayName, String type, String description) {
        // inserting note in db and getting
        // newly inserted note id
        long id = db.insertUser(user, pass, displayName, type, description);

        // get the newly inserted note from db
        User u = db.getUser(id);

        if (u != null) {
            // adding new note to array list at 0 position
            usersList.add(0, u);

            // refreshing the list
            mAdapter.notifyDataSetChanged();
        }
    }
}
