package info.nghiango.sqlite.view;

/**
 * Created by ravi on 20/02/18.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import info.nghiango.sqlite.R;
import info.nghiango.sqlite.db.model.User;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder> {

    private Context context;
    private List<User> usersList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView displayName;
        public TextView dot;
        public TextView description;

        public MyViewHolder(View view) {
            super(view);
            displayName = view.findViewById(R.id.note);
            dot = view.findViewById(R.id.dot);
            description = view.findViewById(R.id.timestamp);
        }
    }


    public UsersAdapter(Context context, List<User> usersList) {
        this.context = context;
        this.usersList = usersList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        User user = usersList.get(position);

        holder.displayName.setText(user.getDisplayName());

        // Displaying dot from HTML character code
        holder.dot.setText(Html.fromHtml("&#8226;"));

        // Formatting and displaying timestamp
        holder.description.setText(user.getDescription());
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    /**
     * Formatting timestamp to `MMM d` format
     * Input: 2018-02-21 00:15:42
     * Output: Feb 21
     */
    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("MMM d");
            return fmtOut.format(date);
        } catch (ParseException e) {

        }

        return "";
    }
}
