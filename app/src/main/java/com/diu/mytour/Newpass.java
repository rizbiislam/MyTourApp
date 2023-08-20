package com.diu.mytour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Newpass extends AppCompatActivity {

    TextInputLayout newpass, confpass;
    Button btnreset;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpass);

        FirebaseApp.initializeApp(this);


        newpass = findViewById(R.id.newpass);
        confpass = findViewById(R.id.confpass);
        btnreset = findViewById(R.id.btnreset);



        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userpassword1 = newpass.getEditText().getText().toString().trim();
                String userpassword2 = confpass.getEditText().getText().toString().trim();

                try {

                    if(userpassword1.equals("")){
                        newpass.setError("Create Password");
                    }
                    if(userpassword2.equals("")){
                        confpass.setError("Confirm Password");
                    }else if (!isNetworkAvailable()) {
                        Toast.makeText(Newpass.this, "Check your internet connection", Toast.LENGTH_LONG).show();
                    }
                    else{
                        String pass1 = newpass.getEditText().getText().toString();
                        String pass2 = confpass.getEditText().getText().toString();

                        if(pass1.equals(pass2)){
                            rootNode = FirebaseDatabase.getInstance();
                            reference = rootNode.getReference().child("Users");

                            /*String phone= regPhoneNo.getEditText().getText().toString().trim();
                            String password = newpass.getEditText().getText().toString().trim();
                            UserHelperClass helperClass = new UserHelperClass(name, phone, password);
                            reference.child(phone).setValue(helperClass);*\*/

                        }
                        else {
                            Toast.makeText(Newpass.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                        }
                    }

                }catch (Exception e){
                    Toast.makeText(Newpass.this, "Error"+e+" ", Toast.LENGTH_SHORT).show();

                }

            }


        });
    }
    public void loginUser(View view) {
        Intent intent = new Intent(Newpass.this, LogIn.class);
        startActivity(intent);
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}