package com.diu.mytour;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SetPlanActivity extends AppCompatActivity {
    private static final int PReqCode = 2 ;
    private static final int REQUESCODE = 2 ;
    ImageView popupPlanImage;
    Button popupPlanBtn;
    TextView popupCaption, popupContact, popupPrice, popupDescription, popupName;
    ProgressBar popupClickProgress;
    Uri pickedImgUri = null;
    TextView suggestion;
    LinearLayout linear, linear2;
    String name, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_plan);

        SharedPreferences sharedPreferences = getSharedPreferences("info",MODE_PRIVATE);
        name = sharedPreferences.getString("name","");
        phone = sharedPreferences.getString("phone","");

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

                if (!popupCaption.getText().toString().isEmpty()
                        && pickedImgUri != null ) {
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Plan_images");
                    final StorageReference imageFilePath = storageReference.child(pickedImgUri.getLastPathSegment());
                    imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageDownlaodLink = uri.toString();
                                    Plan plan = new Plan(popupCaption.getText().toString(),name,popupContact.getText().toString(),popupPrice.getText().toString(),phone,popupDescription.getText().toString(),
                                            imageDownlaodLink,false);

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

                }
                else {
                    showMessage("Please fill up all input fields") ;
                    popupPlanBtn.setVisibility(View.VISIBLE);
                    popupClickProgress.setVisibility(View.INVISIBLE);

                }
            }
        });

    }


    private void checkAndRequestForPermission() {
        Toast.makeText(SetPlanActivity.this,"Please accept required permission", Toast.LENGTH_SHORT).show();

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
        startActivityForResult(galleryIntent,REQUESCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null ) {

            pickedImgUri = data.getData() ;
            popupPlanImage.setImageURI(pickedImgUri);
            suggestion.setVisibility(View.INVISIBLE);
        }
    }

    private void addPlan(Plan plan) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Plans").push();
        String key = myRef.getKey();
        plan.setplanID(key);
        myRef.setValue(plan).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                showMessage("Plan has been submitted for review");
                popupClickProgress.setVisibility(View.INVISIBLE);
                popupCaption.setText("");
                popupContact.setText("");
                popupPrice.setText("");
                popupDescription.setText("");
                recreate();

            }
        });
    }


    private void showMessage(String message) {
        Toast.makeText(SetPlanActivity.this,message, Toast.LENGTH_LONG).show();
    }


}
