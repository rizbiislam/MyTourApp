package com.diu.mytour;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    TextInputLayout regName, regPhoneNo, regPassword, confmPassword;
    Button regBtn, regToLoginBtn;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        regName = findViewById(R.id.reg_name);
        regPhoneNo = findViewById(R.id.reg_phoneNo);
        regPassword = findViewById(R.id.reg_password);
        confmPassword = findViewById(R.id.confm_password);
        regBtn = findViewById(R.id.reg_btn);
        regToLoginBtn = findViewById(R.id.reg_login_btn);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username= regName.getEditText().getText().toString();
                String userphone = regPhoneNo.getEditText().getText().toString();
                String userpassword1 = regPassword.getEditText().getText().toString();
                String userpassword2 = confmPassword.getEditText().getText().toString();

                if(username.equals("")){
                    regName.setError("Enter Name");
                }
                if(userphone.equals("")){
                    regPhoneNo.setError("Enter Phone No");
                }
                if(userpassword1.equals("")){
                    regPassword.setError("Create Password");
                }
                if(userpassword2.equals("")){
                    confmPassword.setError("Confirm Password");
                }else if (!isNetworkAvailable()) {
                        Toast.makeText(SignUp.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        String pass1 = regPassword.getEditText().getText().toString();
                        String pass2 = confmPassword.getEditText().getText().toString();

                        if(pass1.equals(pass2)){
                            rootNode = FirebaseDatabase.getInstance();
                            reference = rootNode.getReference().child("Users");

                            String name = regName.getEditText().getText().toString();
                            String phone= regPhoneNo.getEditText().getText().toString();
                            String password = regPassword.getEditText().getText().toString();
                            UserHelperClass helperClass = new UserHelperClass(name, phone, password);
                            reference.child(phone).setValue(helperClass);

                            Intent intent = new Intent(SignUp.this,Varification.class);
                            intent.putExtra("phoneNo", phone);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(SignUp.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                        }
                    }

                }


        });

    }


    public void loginUser(View view) {
        Intent intent = new Intent(SignUp.this, LogIn.class);
        startActivity(intent);
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}