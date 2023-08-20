// AdminTableAdapter.java
package com.diu.mytour;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdminTableAdapter extends RecyclerView.Adapter<AdminTableAdapter.AdminViewHolder> {

    private Context context;
    private List<AdminHelperClass> adminList;

    public AdminTableAdapter(Context context, List<AdminHelperClass> adminList) {
        this.context = context;
        this.adminList = adminList;
    }

    @NonNull
    @Override
    public AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_admin, parent, false);
        return new AdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminViewHolder holder, int position) {
        if (position == 0) {
            // Display Column Titles in Bold
            holder.textViewName.setText("Name");
            holder.textViewName.setTextColor(context.getResources().getColor(android.R.color.black));
            holder.textViewName.setTypeface(null, android.graphics.Typeface.BOLD);

            holder.textViewEmail.setText("Email");
            holder.textViewEmail.setTextColor(context.getResources().getColor(android.R.color.black));
            holder.textViewEmail.setTypeface(null, android.graphics.Typeface.BOLD);

            holder.textViewPhone.setText("Phone");
            holder.textViewPhone.setTextColor(context.getResources().getColor(android.R.color.black));
            holder.textViewPhone.setTypeface(null, android.graphics.Typeface.BOLD);

            holder.textViewJoiningDate.setText("Joining Date");
            holder.textViewJoiningDate.setTextColor(context.getResources().getColor(android.R.color.black));
            holder.textViewJoiningDate.setTypeface(null, android.graphics.Typeface.BOLD);

            holder.textViewAddress.setText("Address");
            holder.textViewAddress.setTextColor(context.getResources().getColor(android.R.color.black));
            holder.textViewAddress.setTypeface(null, android.graphics.Typeface.BOLD);
        } else {
            // Display Admin Data
            AdminHelperClass admin = adminList.get(position - 1);
            holder.textViewName.setText(admin.getName());
            holder.textViewEmail.setText(admin.getEmail());
            holder.textViewPhone.setText(admin.getPhone());
            holder.textViewJoiningDate.setText(admin.getJoiningDate());
            holder.textViewAddress.setText(admin.getAddress());
        }
    }

    @Override
    public int getItemCount() {
        // Add one for the column titles row
        return adminList.size() + 1;
    }

    public static class AdminViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewEmail, textViewPhone, textViewJoiningDate, textViewAddress;

        public AdminViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewPhone = itemView.findViewById(R.id.textViewPhone);
            textViewJoiningDate = itemView.findViewById(R.id.textViewJoiningDate);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
        }
    }
}
