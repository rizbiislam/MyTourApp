package com.diu.mytour;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hbb20.CountryCodePicker;

public class SignUp extends AppCompatActivity {
    static int PReqCode = 1;
    static int REQUESCODE = 1;
    TextInputLayout regName, regPhoneNo, regPassword, confmPassword;
    Button regBtn, regToLoginBtn;
    Uri pickedImgUri;
    ImageView profilePicture;
    TextView suggestion;
    CountryCodePicker countryCodePicker;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        suggestion = findViewById(R.id.suggestion);
        regName = findViewById(R.id.reg_name);
        regPhoneNo = findViewById(R.id.reg_phoneNo);
        regPassword = findViewById(R.id.reg_password);
        confmPassword = findViewById(R.id.confm_password);
        regBtn = findViewById(R.id.reg_btn);
        regToLoginBtn = findViewById(R.id.reg_login_btn);

        countryCodePicker = findViewById(R.id.countryCodePicker);
        countryCodePicker.setDefaultCountryUsingNameCode("BD");
        builder = new AlertDialog.Builder(this);

        profilePicture = findViewById(R.id.regUserPhoto);

        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= 22) {
                    checkAndRequestForPermission();
                } else {
                    openGallery();
                }
            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = regName.getEditText().getText().toString();
                String userphone = regPhoneNo.getEditText().getText().toString();
                String userpassword1 = regPassword.getEditText().getText().toString();
                String userpassword2 = confmPassword.getEditText().getText().toString();

                if (username.equals("")) {
                    regName.setError("Enter Name");
                }
                if (userphone.equals("")) {
                    regPhoneNo.setError("Enter Phone No");
                }
                if (userpassword1.equals("")) {
                    regPassword.setError("Create Password");
                }
                if (userpassword2.equals("")) {
                    confmPassword.setError("Confirm Password");
                } else if (!isNetworkAvailable()) {
                    Toast.makeText(SignUp.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                } else {
                    String pass1 = regPassword.getEditText().getText().toString();
                    String pass2 = confmPassword.getEditText().getText().toString();

                    if (pass1.equals(pass2)) {
                        String ephone = regPhoneNo.getEditText().getText().toString();


                        // Check if the phone number exists in the database
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
                        Query phoneQuery = reference.orderByChild("phone").equalTo(ephone);
                        phoneQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                if (dataSnapshot.exists()) {
                                    // Phone number already exists, show a message
                                    builder.setTitle("Alert!")
                                            .setMessage("User with this phone number already exists!")
                                            .setCancelable(true)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {


                                                }
                                            })

                                            .show();
                                   // Toast.makeText(SignUp.this, "User with this phone number already exists!", Toast.LENGTH_SHORT).show();
                                } else {
                                    String pass1 = regPassword.getEditText().getText().toString();
                                    String pass2 = confmPassword.getEditText().getText().toString();

                                    if (pass1.equals(pass2)) {

                                        String name = regName.getEditText().getText().toString();
                                        String phone = regPhoneNo.getEditText().getText().toString();
                                        String phone2 = regPhoneNo.getEditText().getText().toString();
                                        String password = regPassword.getEditText().getText().toString();
                                        if (phone2.startsWith("0")) {
                                            phone2 = phone2.substring(1);


                                            String phone1 = countryCodePicker.getSelectedCountryCodeWithPlus() + phone2;
                                            SharedPreferences sharedPreferences22 = getSharedPreferences("info", MODE_PRIVATE);
                                            SharedPreferences.Editor editor22 = sharedPreferences22.edit();
                                            editor22.putString("phoneNo", phone1);
                                            editor22.apply();
                                        }


                                        Intent intent = new Intent(SignUp.this, Varification.class);
                                        intent.putExtra("name", name);
                                        intent.putExtra("phoneNo", phone);
                                        intent.setData(pickedImgUri);
                                        intent.putExtra("password", password);
                                        startActivity(intent);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(SignUp.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
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

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, REQUESCODE);
    }

    private void checkAndRequestForPermission() {
        if (ContextCompat.checkSelfPermission(SignUp.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(SignUp.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(SignUp.this, "Please accept required permission", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(SignUp.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }
        } else
            openGallery();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null) {
            pickedImgUri = data.getData();
            profilePicture.setImageURI(pickedImgUri);
            suggestion.setVisibility(View.INVISIBLE);
        }
    }
}
