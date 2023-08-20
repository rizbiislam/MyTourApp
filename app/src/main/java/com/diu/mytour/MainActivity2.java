package com.diu.mytour;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class MainActivity2 extends AppCompatActivity {

    Button verify_btn1;
    TextInputLayout verification1;
    Button find_acunt;
    TextInputLayout find_phone;
    ProgressBar progressBar1;
    CountryCodePicker countryCodePicker1;
    String verificationCodeBySystem;
    String phoneNo1,phoneNo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        find_acunt = findViewById(R.id.find_acunt);
        find_phone = findViewById(R.id.find_phone);
        verify_btn1 = findViewById(R.id.verify_btn1);
        verification1 = findViewById(R.id.verification_code1);
        progressBar1 = findViewById(R.id.progress_bar1);
        progressBar1.setVisibility(View.GONE);

        countryCodePicker1 = findViewById(R.id.countryCodePicker1);
        countryCodePicker1.setDefaultCountryUsingNameCode("BD");

        find_acunt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNo1 = find_phone.getEditText().getText().toString().trim();
                phoneNo2 = find_phone.getEditText().getText().toString().trim();

                if (!phoneNo1.isEmpty()) {
                    if (phoneNo2.startsWith("0")) {
                        phoneNo2 = phoneNo2.substring(1);
                        String phone1111 = countryCodePicker1.getSelectedCountryCodeWithPlus() + phoneNo2;


                        sendVerificationCodeToUser(phone1111);
                    }
                } else {
                    Toast.makeText(MainActivity2.this, "Please enter a phone number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        verify_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String verificationCode = verification1.getEditText().getText().toString().trim();
                if (!verificationCode.isEmpty()) {
                    verifyCode(verificationCode);
                } else {
                    Toast.makeText(MainActivity2.this, "Please enter the verification code", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method to send verification code to the provided phone number
    private void sendVerificationCodeToUser(String phone1111) {
        progressBar1.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone1111,
                60, // Timeout duration
                TimeUnit.SECONDS,
                this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        // Auto-retrieval or instant verification of code
                        String code = phoneAuthCredential.getSmsCode();
                        if (code != null) {
                            verification1.getEditText().setText(code);
                            // Automatically verify the code if received instantly
                            verifyCode(code);
                        }
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        // Verification failed. Display an error message.
                        progressBar1.setVisibility(View.GONE);
                        Toast.makeText(MainActivity2.this, "Verification Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        Toast.makeText(MainActivity2.this,"code sent at"+phone1111,Toast.LENGTH_SHORT).show();
                        progressBar1.setVisibility(View.GONE);
                        verificationCodeBySystem = verificationId;

                    }
                }
        );
    }

    // Method to verify the code entered by the user
    private void verifyCode(String code) {
        progressBar1.setVisibility(View.VISIBLE);
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem, code);
        signInWithPhoneAuthCredential(credential);
    }

    // Method to sign in with the phone auth credential
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar1.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Verification successful. User is signed in.
                            Toast.makeText(MainActivity2.this, "Verification Successful", Toast.LENGTH_SHORT).show();
                            Intent ii = new Intent(MainActivity2.this, UpdatePassword.class);
                            ii.putExtra("phoneno111",phoneNo1);
                            startActivity(ii);
                        } else {
                            // Verification failed. Display an error message.
                            Toast.makeText(MainActivity2.this, "Verification Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
