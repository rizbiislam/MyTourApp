package com.diu.mytour;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Locale;

public class PlanDetailActivity extends AppCompatActivity {
    ImageView planImg;
    TextView planCaption,planPrice,planContact2,planDescription,planUser;
    String planID;
    FirebaseDatabase firebaseDatabase;
    String Contact, Contact2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_detail);

        planImg =findViewById(R.id.plan_detail_img);
        planCaption = findViewById(R.id.plan_detail_title);
        planPrice = findViewById(R.id.plan_detail_price);
        planContact2 = findViewById(R.id.plan_detail_contact2);
        planDescription = findViewById(R.id.plan_detail_description);
        planUser = findViewById(R.id.row_plan_profile_name);

        firebaseDatabase = FirebaseDatabase.getInstance();


        String planImage = getIntent().getExtras().getString("planImage") ;
        Glide.with(this).load(planImage).into(planImg);

        String planTitle = getIntent().getExtras().getString("planCaption");
        planCaption.setText(planTitle);

        String user = getIntent().getExtras().getString("planName");
        planUser.setText(user);

        String planPricee = getIntent().getExtras().getString("planPrice");
        planPrice.setText(planPricee);

        Contact2 = getIntent().getExtras().getString("planContact2");
        planContact2.setText(Contact2);

        String Description = getIntent().getExtras().getString("planDescription");
        planDescription.setText(Description);

        planID = getIntent().getExtras().getString("planID");

    }

    private String timestampToString(long time) {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy",calendar).toString();
        return date;
    }

    public void msg(View view) {
        Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "sms:" + Contact2));
        intent.putExtra( "sms_body", "Hello Dear, I'm interested to join this tour plan" );
        startActivity(intent);
    }

    public void mail(View view) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{Contact});
        i.putExtra(Intent.EXTRA_SUBJECT, "Joining Tour");
        i.putExtra(Intent.EXTRA_TEXT   , "Hello Dear, I'm interested to join this tour plan");
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(PlanDetailActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
