package com.diu.mytour;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddAdminActivity extends AppCompatActivity {

    private EditText etAdminName, etAdminEmail, etAdminPhone, etAdminPassword, etJoiningDate, etLeftDate, etAddress, etSecretCode;
    private Button addButton;

    // Set your correct secret code here
    private static final String CORRECT_SECRET_CODE = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);

        etAdminName = findViewById(R.id.etAdminName);
        etAdminEmail = findViewById(R.id.etAdminEmail);
        etAdminPhone = findViewById(R.id.etAdminPhone);
        etAdminPassword = findViewById(R.id.etAdminPassword);
        etJoiningDate = findViewById(R.id.etJoiningDate);
        etLeftDate = findViewById(R.id.etLeftDate);
        etAddress = findViewById(R.id.etAddress);
        etSecretCode = findViewById(R.id.etSecretCode);

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String secretCode = etSecretCode.getText().toString().trim();
                if (TextUtils.isEmpty(secretCode) || !secretCode.equals(CORRECT_SECRET_CODE)) {
                    Toast.makeText(AddAdminActivity.this, "Invalid secret code", Toast.LENGTH_SHORT).show();
                    return;
                }

                addAdminToFirebase();
            }
        });
    }

    private void addAdminToFirebase() {
        String name = etAdminName.getText().toString().trim();
        String email = etAdminEmail.getText().toString().trim();
        String phone = etAdminPhone.getText().toString().trim();
        String password = etAdminPassword.getText().toString().trim();
        String joiningDate = etJoiningDate.getText().toString().trim();
        String leftDate = etLeftDate.getText().toString().trim();
        String address = etAddress.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone)
                || TextUtils.isEmpty(password) || TextUtils.isEmpty(joiningDate) || TextUtils.isEmpty(address)) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference adminRef = FirebaseDatabase.getInstance().getReference().child("admin");
        DatabaseReference emailRef = adminRef.child(email.replace(".", ",")); // Replace '.' with ',' to avoid Firebase key issues

        AdminHelperClass admin = new AdminHelperClass(name, email, phone, password, joiningDate, leftDate, address);
        emailRef.setValue(admin, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@NonNull DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null) {
                    Toast.makeText(AddAdminActivity.this, "Admin added successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddAdminActivity.this, "Failed to add admin: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
