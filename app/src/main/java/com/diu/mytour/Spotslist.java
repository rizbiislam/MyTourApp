package com.diu.mytour;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Spotslist extends AppCompatActivity{
    Button verify_btn;
    TextInputLayout verification;
    ProgressBar progressBar;
    String verificationCodeBySystem;
    String phoneNo;
    private String verificationId;

    private FirebaseAuth firebaseAuth;
    private PhoneAuthProvider.ForceResendingToken resendToken;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_varification);

        verify_btn = findViewById(R.id.verify_btn);
        verification = findViewById(R.id.verification_code);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        firebaseAuth = FirebaseAuth.getInstance();


            String phoneNumber = getIntent().getStringExtra("+880"+"phoneNo");
            if (!phoneNumber.isEmpty()) {
                sendVerificationCode(phoneNumber);
            } else {
                Toast.makeText(Spotslist.this, "Please enter a phone number.", Toast.LENGTH_SHORT).show();
            }


        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String verificationCode = getIntent().getStringExtra("+880"+"phoneNo");
                if (!verificationCode.isEmpty()) {
                    verifyCode(verificationCode);
                } else {
                    Toast.makeText(Spotslist.this, "Please enter the verification code.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        verificationCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                // Auto-retrieval or instant verification is successful.
                String code = phoneAuthCredential.getSmsCode();
                if (code != null) {

                    verification.getEditText().toString().trim();
                    verifyCode(code);
                }
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("SMS Verification", "Verification failed: " + e.getMessage());
                Toast.makeText(Spotslist.this, "Verification failed.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                verificationId = s;
                resendToken = forceResendingToken;
                Toast.makeText(Spotslist.this, "Verification code sent.", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void sendVerificationCode(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                verificationCallbacks);
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(Spotslist.this, "Verification successful.", Toast.LENGTH_SHORT).show();
                        // Handle successful verification, e.g., proceed to the next activity
                    } else {
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(Spotslist.this, "Invalid verification code.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Spotslist.this, "Verification failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}