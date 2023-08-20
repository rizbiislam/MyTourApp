package com.diu.mytour;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class PlanDetailActivity extends AppCompatActivity {
    ImageView planImg;
    Button msg,booked,approve_plan;
    TextView planCaption, planPrice, planContact, planContact2, planDescription, planUser, editTextDate1;
    String planID;
    FirebaseDatabase firebaseDatabase;
    String Contact, Contact2;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_detail);

        planImg = findViewById(R.id.plan_detail_img);
        planCaption = findViewById(R.id.plan_detail_title);
        planPrice = findViewById(R.id.plan_detail_price);
        msg = findViewById(R.id.msg);
        booked = findViewById(R.id.booked);
        planContact2 = findViewById(R.id.plan_detail_contact2);
        planDescription = findViewById(R.id.plan_detail_description);
        editTextDate1 = findViewById(R.id.editTextDate1);
        planUser = findViewById(R.id.row_plan_profile_name);
        approve_plan = findViewById(R.id.approve_plan);

        SharedPreferences sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);

        phone = sharedPreferences.getString("phone", "");

        firebaseDatabase = FirebaseDatabase.getInstance();


        String planImage = getIntent().getExtras().getString("planImage");
        Glide.with(this).load(planImage).into(planImg);

        String planTitle = getIntent().getExtras().getString("planCaption");
        planCaption.setText(planTitle);

        String user = getIntent().getExtras().getString("planName");
        planUser.setText(user);

        String planPricee = getIntent().getExtras().getString("planPrice");
        planPrice.setText(planPricee);

        Contact2 = getIntent().getExtras().getString("planContact2");
        planContact2.setText(Contact2);

        String editTextDate = getIntent().getExtras().getString("expireDate");
        // editTextDate1.setText(editTextDate);

        String Description = getIntent().getExtras().getString("planDescription");
        planDescription.setText(Description);

        Boolean planStatus = getIntent().getExtras().getBoolean("planStatus");

        planID = getIntent().getExtras().getString("planID");

        long expireDateTime = parseDateToTimestamp(editTextDate);

        if(planStatus==false){
            msg.setVisibility(View.GONE);
            booked.setVisibility(View.GONE);
        }
        else{
            approve_plan.setVisibility(View.GONE);
        }


        if (isExpired(expireDateTime)) {

            editTextDate1.setText("Date expired");
            int textColor = ContextCompat.getColor(this, R.color.Red);
            editTextDate1.setTextColor(textColor);
            msg.setEnabled(false);
            msg.setBackgroundColor(textColor);

            firebaseDatabase.getReference("Plans").child(planID).child("planStatus").setValue(false);

        } else {
            String formattedDate = timestampToString(expireDateTime);
            editTextDate1.setText(formattedDate);
            int textColor = ContextCompat.getColor(this, R.color.Green);
            msg.setEnabled(true);
            msg.setBackgroundColor(textColor);
        }


        approve_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Activity 2
                SharedPreferences sharedPreferences2222 = getSharedPreferences("adminPreferences", Context.MODE_PRIVATE);
                String myString = sharedPreferences2222.getString("myStringKey", "default value");
                firebaseDatabase.getReference("Plans").child(planID).child("approvedby").setValue(myString);

                firebaseDatabase.getReference("Plans").child(planID).child("planStatus").setValue(true);
                finish();
            }
        });

        booked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Contact2.equals(phone)) {
                    // If the user's phone matches the plan's contact
                    booked.setEnabled(false);
                    booked.setText("Already Booked");
                    int textColor1 = ContextCompat.getColor(PlanDetailActivity.this, R.color.Red);
                    booked.setTextColor(textColor1);
                    String currentDate1 = getCurrentDate();

                    firebaseDatabase.getReference("Plans").child(planID).child("planStatus").setValue(false);
                    firebaseDatabase.getReference("Plans").child(planID).child("expireDate").setValue(currentDate1);

                    finish();
                }
              //  firebaseDatabase.getReference("admin").child(planID).child("planStatus").setValue(false);

                else {

                    booked.setEnabled(true);
                    booked.setText("Book Now");
                    int textColor1 = ContextCompat.getColor(PlanDetailActivity.this, R.color.Green);
                    booked.setTextColor(textColor1);
                }
            }
        });




        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + Contact2));
                intent.putExtra("sms_body", "Hello Dear, I'm interested to join this tour plan");
                startActivity(intent);
            }
        });



    }

    private String getCurrentDate() {
        // Get the current date and time
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        // Format the date as "DD/MM/YYYY"
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(date);
    }

    private String timestampToString(long time) {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy", calendar).toString();
        return date;
    }

   /* public void msgs(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + Contact2));
        intent.putExtra("sms_body", "Hello Dear, I'm interested to join this tour plan");
        startActivity(intent);
    }*/

    public void mail(View view) {
        /*
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"mytourappofficial@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT,"Forgot Password");
        intent.putExtra(Intent.EXTRA_TEXT, "Enter Phone No:\nLast Password(optional):");
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent,"Send a mail" ));*/
        Intent i = new Intent(Intent.ACTION_SEND);
        //  i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{Contact});
        i.putExtra(Intent.EXTRA_SUBJECT, "Joining Tour");
        i.putExtra(Intent.EXTRA_TEXT, "Hello Dear, I'm interested to join this tour plan");
        i.setType("message/rfc822");
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(PlanDetailActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isExpired(long timestamp) {
        // Get the current time in milliseconds
        long currentTime = System.currentTimeMillis();

        // Compare the timestamp with the current time
        return timestamp < currentTime;
    }

    // Helper method to parse "DD/MM/YYYY" or "DDMMYYYY" date format to timestamp
    private long parseDateToTimestamp(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            Date date = sdf.parse(dateStr);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            // If parsing fails, try parsing without the slashes
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy", Locale.ENGLISH);
                Date date = sdf.parse(dateStr);
                return date.getTime();
            } catch (ParseException ex) {
                ex.printStackTrace();
                return 0L;
            }
        }
    }
}