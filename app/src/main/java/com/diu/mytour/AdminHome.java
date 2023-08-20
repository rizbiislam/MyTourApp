package com.diu.mytour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        Button add_admins = findViewById(R.id.add_admins);
        Button inactive_plan = findViewById(R.id.inactive_plan);
        Button active_plan = findViewById(R.id.active_plan);
        Button view_admin = findViewById(R.id.view_admin);
        Button view_user = findViewById(R.id.view_user);

        inactive_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean statuses = false; // Use the primitive boolean type

                Intent intent = new Intent(AdminHome.this, TourPlanActivity.class);

                intent.putExtra("STATUS_KEY", statuses); // Pass the status data with the intent
                startActivity(intent);
            }
        });

        add_admins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, AddAdminActivity.class);
                startActivity(intent);
            }
        });
        view_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this,ViewUsersActivity.class);
                startActivity(intent);
            }
        });

        active_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean statuses = true; // Use the primitive boolean type

                Intent intent = new Intent(AdminHome.this, TourPlanActivity.class);

                intent.putExtra("STATUS_KEY", statuses); // Pass the status data with the intent
                startActivity(intent);
            }
        });
        view_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AdminHome.this, ViewAdminsActivity.class);
                startActivity(intent);
            }
        });
    }
}