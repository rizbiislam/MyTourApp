package com.diu.mytour;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class TourPlanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_plan);


        getSupportFragmentManager().beginTransaction().replace(R.id.container,new TourPlanFragment()).commit();

    }

}
