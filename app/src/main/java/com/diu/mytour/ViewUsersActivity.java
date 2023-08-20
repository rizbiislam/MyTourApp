package com.diu.mytour;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewUsersActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> userList;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);

        listView = findViewById(R.id.userListView);
        userList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userList);
        listView.setAdapter(arrayAdapter);

        // Reference to the "Users" node in Firebase Realtime Database
        String phoneNumber = "Phone1"; // Replace "Phone1" with the actual phone number of the user you want to retrieve data for
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");

        usersRef.child(phoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot phoneSnapshot : dataSnapshot.getChildren()) {
                    UserHelperClass user = phoneSnapshot.getValue(UserHelperClass.class);
                    if (user != null) {
                        // Add the user's name to the userList
                        userList.add(user.getName());
                    }
                }

                // Update the ListView with the retrieved user names
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that occur while retrieving data
                Toast.makeText(ViewUsersActivity.this, "Failed to retrieve user data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
