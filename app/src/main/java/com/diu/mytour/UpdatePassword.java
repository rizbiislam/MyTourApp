package com.diu.mytour;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdatePassword extends AppCompatActivity {

    private TextInputLayout newPasswordLayout;
    private Button updatePasswordButton;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        phoneNumber = getIntent().getStringExtra("phoneno111");

        newPasswordLayout = findViewById(R.id.new_password_layout);
        updatePasswordButton = findViewById(R.id.update_password_button);

        updatePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword();
            }
        });
    }

    private void updatePassword() {
        String newPassword = newPasswordLayout.getEditText().getText().toString().trim();

        if (TextUtils.isEmpty(newPassword)) {
            newPasswordLayout.setError("Enter new password");
            return;
        } else {
            newPasswordLayout.setError(null);
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user.updatePassword(newPassword)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(UpdatePassword.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                                updatePasswordInDatabase(newPassword);
                            } else {
                                Toast.makeText(UpdatePassword.this, "Password update failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(UpdatePassword.this, "User not logged in.", Toast.LENGTH_SHORT).show();
        }
    }

    private void updatePasswordInDatabase(String newPassword) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(phoneNumber);
        reference.child("password").setValue(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UpdatePassword.this, "Password updated in the database", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UpdatePassword.this, LogIn.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(UpdatePassword.this, "Failed to update password in the database", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
