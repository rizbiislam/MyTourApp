package com.diu.mytour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ReceiveNotificationActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<Plan> planList;
    Query query;
    Context c;
    TextView uname, ulocation;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_notification);

        uname = findViewById(R.id.name);
        ulocation = findViewById(R.id.location);
        image = findViewById(R.id.image);

        SharedPreferences sharedPreferences = getSharedPreferences("checked", MODE_PRIVATE);
        String number = sharedPreferences.getString("number", "");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
        Query checkUser = reference.orderByChild("phone").equalTo(number);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String imageDB = dataSnapshot.child(number).child("image").getValue(String.class);
                    Glide.with(ReceiveNotificationActivity.this).load(imageDB).into(image);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(ReceiveNotificationActivity.this, "Request cancelled", Toast.LENGTH_SHORT).show();
            }

        });

        if (getIntent().hasExtra("time")) {
            String name = getIntent().getStringExtra("time");
            String location = getIntent().getStringExtra("link");
            uname.setText(name);
            ulocation.setText(location);
            ((ClipboardManager) getSystemService(CLIPBOARD_SERVICE)).setText(location);
            Toast.makeText(getApplicationContext(), "Location copied to Clipboard", Toast.LENGTH_SHORT).show();
        }

        ulocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location = getIntent().getStringExtra("link");
                if (location != null && !location.isEmpty()) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(location));
                    startActivity(browserIntent);
                } else {
                    Toast.makeText(ReceiveNotificationActivity.this, "No location found!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
