package com.diu.mytour;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.LocationServices;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LogIn extends AppCompatActivity {
    private static final int REQUEST_LOCATION = 1;
    TextInputLayout phone,password;
    ProgressBar progressBar;
    TextView logo_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        logo_name = findViewById(R.id.logo_name);
        logo_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogIn.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        SharedPreferences sharedPreferences = getSharedPreferences("checked",MODE_PRIVATE);
        String check = sharedPreferences.getString("remember","");
        if(check.equals("true")){
            Intent intent = new Intent(LogIn.this,MainActivity.class);
            startActivity(intent);
        }


    }

    public void loginUser(View view) {

        String userphone = phone.getEditText().getText().toString().trim();
        String userpassword = password.getEditText().getText().toString().trim();

        if(userphone.equals("")){
            phone.setError("Enter Phone");
        }
        if(userpassword.equals("")){
            password.setError("Enter Password");
        }else if (!isNetworkAvailable()) {
                Toast.makeText(LogIn.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
            }
            else{
                if (!validateUsername() | !validatePassword()) {
                    return;
                } else {
                    isUser();
                }
            }
        }

    private Boolean validateUsername() {
        String val = phone.getEditText().getText().toString();
        if (val.isEmpty()) {
            phone.setError("Field cannot be empty");
            return false;
        } else {
            phone.setError(null);
            phone.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePassword() {
        String val = password.getEditText().getText().toString();
        if (val.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }
    private void isUser() {
        progressBar.setVisibility(View.VISIBLE);
        final String userEnteredphone = phone.getEditText().getText().toString().trim();
        final String userEnteredPassword = password.getEditText().getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
        Query checkUser = reference.orderByChild("phone").equalTo(userEnteredphone);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    phone.setError(null);
                    phone.setErrorEnabled(false);
                    String passwordFromDB = dataSnapshot.child(userEnteredphone).child("password").getValue(String.class);
                    if (passwordFromDB.equals(userEnteredPassword)) {
                        phone.setError(null);
                        phone.setErrorEnabled(false);
                        String nameFromDB = dataSnapshot.child(userEnteredphone).child("name").getValue(String.class);
                        String phoneNoFromDB = dataSnapshot.child(userEnteredphone).child("phone").getValue(String.class);
                        Intent intent = new Intent(LogIn.this, MainActivity.class);
                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("phone", phoneNoFromDB);
                        startActivity(intent);

                        SharedPreferences sharedPreferences = getSharedPreferences("checked",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("remember", "true");
                        editor.putString("number", String.valueOf(phoneNoFromDB));
                        editor.apply();

                        SharedPreferences sharedPreferences2 = getSharedPreferences("info",MODE_PRIVATE);
                        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                        editor2.putString("name", nameFromDB);
                        editor2.putString("phone", phoneNoFromDB);
                        editor2.apply();

                    } else {
                        progressBar.setVisibility(View.GONE);
                        password.setError("Wrong Password");
                        password.requestFocus();
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                    phone.setError("No such User exist");
                    phone.requestFocus();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(LogIn.this, "Request cancelled", Toast.LENGTH_SHORT).show();
            }

        });
    }

    public void register(View view) {
        Intent intent = new Intent(LogIn.this, SignUp.class);
        startActivity(intent);
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public void forgot(View view) {


        Intent intent = new Intent(LogIn.this,MainActivity2.class);
        startActivity(intent);

    }
}