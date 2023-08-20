// ViewAdminActivity.java
package com.diu.mytour;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewAdminsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewAdmins;
    private List<AdminHelperClass> adminList;
    private AdminTableAdapter adminAdapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_admins);

        recyclerViewAdmins = findViewById(R.id.recyclerViewAdmins);
        recyclerViewAdmins.setLayoutManager(new LinearLayoutManager(this));
        adminList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("admin");

        loadAdminsData();
    }

    private void loadAdminsData() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    AdminHelperClass admin = snapshot.getValue(AdminHelperClass.class);
                    if (admin != null) {
                        adminList.add(admin);
                    }
                }

                adminAdapter = new AdminTableAdapter(ViewAdminsActivity.this, adminList);
                recyclerViewAdmins.setAdapter(adminAdapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error if needed
            }
        });
    }
}
