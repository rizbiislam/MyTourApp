package com.diu.mytour;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class TourPlanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_plan);

        boolean status = getIntent().getBooleanExtra("STATUS_KEY", false);

        // Pass the status data to the TourPlanFragment
        TourPlanFragment fragment = TourPlanFragment.newInstance(status);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}
