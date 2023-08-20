package com.diu.mytour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ReceiveNotificationActivity extends AppCompatActivity {
    Context c;
    TextView uname, ulocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_notification);

        uname= findViewById(R.id.name);
        ulocation = findViewById(R.id.location);

        if (getIntent().hasExtra("time")){
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
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(location));
                startActivity(browserIntent);
            }
        });

    }

}