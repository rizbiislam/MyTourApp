package com.diu.mytour;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends ArrayAdapter<UserHelperClass> {
    private Context context;
    private int resource;
    private List<UserHelperClass> userList;

    public UserAdapter(Context context, int resource, List<UserHelperClass> userList) {
        super(context, resource, userList);
        this.context = context;
        this.resource = resource;
        this.userList = userList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        UserHelperClass user = userList.get(position);

        ImageView userImageView = convertView.findViewById(R.id.userImageView);
        TextView userNameTextView = convertView.findViewById(R.id.userNameTextView);
        TextView userPhoneTextView = convertView.findViewById(R.id.userPhoneTextView);
        Button removeButton = convertView.findViewById(R.id.removeButton);

        userNameTextView.setText(user.getName());
        userPhoneTextView.setText(user.getPhone());

        if (user.getImage() != null && !user.getImage().isEmpty()) {
            Picasso.get().load(user.getImage()).into(userImageView);
        } else {
            // Set a default image if the user does not have an image
            userImageView.setImageResource(R.drawable.logo2);
        }

        // You can add an event listener for the remove button here, if needed.

        return convertView;
    }
}
