package com.diu.mytour;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.concurrent.TimeUnit;

public class Varification extends AppCompatActivity {
    Button verify_btn;
    TextInputLayout verification;
    ProgressBar progressBar;
    String verificationCodeBySystem;
    String phoneNo;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_varification);
        verify_btn = findViewById(R.id.verify_btn);
        verification = findViewById(R.id.verification_code);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        SharedPreferences sharedPreferences22 = getSharedPreferences("info", MODE_PRIVATE);
        String phoneNo = sharedPreferences22.getString("phoneNo", "Sorry,Not found number.");
        sendVerificationCodeToUser(phoneNo);

        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String usercode = verification.getEditText().getText().toString().trim();

                if(usercode.equals("")){
                    verification.setError("Enter Code");
                }else {
                    if (!isNetworkAvailable()) {
                        Toast.makeText(Varification.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        String code = verification.getEditText().getText().toString();

                        if (code.isEmpty() || code.length() < 6) {
                            Toast.makeText(Varification.this, "Please enter valid code", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        progressBar.setVisibility(View.VISIBLE);
                        verifyCode(code);
                    }
                }
            }
        });

    }
    private void sendVerificationCodeToUser(String phoneNo) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNo,
                60,
                TimeUnit.SECONDS,
                Varification.this,
                mCallbacks);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    verificationCodeBySystem = s;
                }

                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                    String code = phoneAuthCredential.getSmsCode();
                    if (code != null) {
                        progressBar.setVisibility(View.VISIBLE);
                        verifyCode(code);
                    }
                    SharedPreferences sharedPreferences22 = getSharedPreferences("info", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences22.edit();
                    editor.clear();
                    editor.apply();
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    Toast.makeText(Varification.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            };
    private void verifyCode(String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem, code);
        signInTheUserByCredentials(credential);
    }
    private void signInTheUserByCredentials(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(Varification.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            CreateUserAccount();

                        } else {
                            Toast.makeText(Varification.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    private void CreateUserAccount() {

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference().child("Users");


        String name = getIntent().getStringExtra("name");
        phoneNo = getIntent().getStringExtra("phoneNo");
        Uri image = getIntent().getData();
        String password = getIntent().getStringExtra("password");
        sendVerificationCodeToUser(phoneNo);


        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("users_photos");
        final StorageReference imageFilePath = mStorage.child(image.getLastPathSegment());
        imageFilePath.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        UserHelperClass helperClass = new UserHelperClass(name, phoneNo, String.valueOf(uri), password);
                        reference.child(phoneNo).setValue(helperClass);
                        updateUI();

                    }
                });
            }
        });


    }

    private void updateUI() {
        Intent intent = new Intent(Varification.this, LogIn.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}