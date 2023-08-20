package com.diu.mytour;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SetPlanActivity extends AppCompatActivity {
    private static final int PReqCode = 2;
    private static final int REQUESCODE = 2;
    ImageView popupPlanImage;
    Button popupPlanBtn;
    EditText expireDate;
    TextView popupCaption, popupContact, popupPrice, popupDescription, popupName;
    ProgressBar popupClickProgress;
    Uri pickedImgUri = null;
    TextView suggestion;
    LinearLayout linear, linear2;
    String name, phone;
    String spinnerData; // Store the selected Spinner data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_plan);

        // Initialize the Spinner and its adapter with the data from the string array resource
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.Area,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Set a listener to capture the selected item from the Spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerData = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinnerData = "";
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
        name = sharedPreferences.getString("name", "");
        phone = sharedPreferences.getString("phone", "");

        linear = findViewById(R.id.linear);
        linear2 = findViewById(R.id.linear2);

        suggestion = findViewById(R.id.suggestion);

        popupPlanImage = findViewById(R.id.popup_img);
        popupCaption = findViewById(R.id.popup_caption);
        popupContact = findViewById(R.id.popup_contact);
        popupPrice = findViewById(R.id.popup_price);
        popupDescription = findViewById(R.id.popup_description);
        popupName = findViewById(R.id.popup_name);
        popupPlanBtn = findViewById(R.id.popup_add);
        expireDate = findViewById(R.id.expireDate);
        popupClickProgress = findViewById(R.id.popup_progressBar);

        popupPlanImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAndRequestForPermission();
            }
        });

        popupPlanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupPlanBtn.setVisibility(View.INVISIBLE);
                popupClickProgress.setVisibility(View.VISIBLE);

                if (!popupCaption.getText().toString().isEmpty() && pickedImgUri != null) {
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Plan_images");
                    final StorageReference imageFilePath = storageReference.child(pickedImgUri.getLastPathSegment());
                    imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageDownloadLink = uri.toString();
                                    String tourDate = expireDate.getText().toString(); // Get the date from the EditText
                                    Plan plan = new Plan(
                                            popupCaption.getText().toString(),
                                            name,
                                            popupContact.getText().toString(),
                                            popupPrice.getText().toString(),
                                            phone,
                                            popupDescription.getText().toString(),
                                            imageDownloadLink,
                                            tourDate, // Set the tour date in the Plan object
                                            false,
                                            spinnerData // Set the selected Spinner data in the Plan object
                                    );

                                    addPlan(plan);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(Exception e) {
                                    showMessage(e.getMessage());
                                    popupClickProgress.setVisibility(View.INVISIBLE);
                                    popupPlanBtn.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                    });

                } else {
                    showMessage("Please fill up all input fields");
                    popupPlanBtn.setVisibility(View.VISIBLE);
                    popupClickProgress.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void checkAndRequestForPermission() {
        Toast.makeText(SetPlanActivity.this, "Please accept required permission", Toast.LENGTH_SHORT).show();
        openGallery();
        if (ContextCompat.checkSelfPermission(SetPlanActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(SetPlanActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Display a rationale explaining why the permission is needed (optional).
                Toast.makeText(SetPlanActivity.this, "Please accept the required permission", Toast.LENGTH_SHORT).show();
            } else {
                // Request the permission.
                ActivityCompat.requestPermissions(SetPlanActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }
        } else {
            openGallery();
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, REQUESCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null) {
            pickedImgUri = data.getData();
            popupPlanImage.setImageURI(pickedImgUri);
            suggestion.setVisibility(View.INVISIBLE);
        }
    }

    private void addPlan(Plan plan) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference plansRef = database.getReference("Plans");
        DatabaseReference myRef = plansRef.push();
        String key = myRef.getKey();
        plan.setPlanID(key);

        // Add the plan data to the "Plans" node using the unique planID
        myRef.setValue(plan).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Plan data added successfully, now add the expireDate and Division as children of the planID
                    myRef.child("expireDate").setValue(plan.getExpireDate()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // ExpireDate added successfully, now add the Division data
                                myRef.child("Division").setValue(plan.getSpinnerData()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            // Set approvedby as empty child
                                            myRef.child("approvedby").setValue("").addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        showMessage("Plan has been submitted for review");
                                                        popupClickProgress.setVisibility(View.INVISIBLE);
                                                        popupCaption.setText("");
                                                        popupContact.setText("");
                                                        popupPrice.setText("");
                                                        popupDescription.setText("");
                                                        expireDate.setText("");
                                                        recreate();
                                                    } else {
                                                        showMessage("Failed to add approvedby data");
                                                        popupClickProgress.setVisibility(View.INVISIBLE);
                                                        popupPlanBtn.setVisibility(View.VISIBLE);
                                                    }
                                                }
                                            });
                                        } else {
                                            showMessage("Failed to add Division data");
                                            popupClickProgress.setVisibility(View.INVISIBLE);
                                            popupPlanBtn.setVisibility(View.VISIBLE);
                                        }
                                    }
                                });
                            } else {
                                showMessage("Failed to add expireDate");
                                popupClickProgress.setVisibility(View.INVISIBLE);
                                popupPlanBtn.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                } else {
                    showMessage("Failed to add plan data");
                    popupClickProgress.setVisibility(View.INVISIBLE);
                    popupPlanBtn.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void showMessage(String message) {
        Toast.makeText(SetPlanActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
