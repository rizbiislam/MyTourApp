package com.diu.mytour;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class SpotActivity extends AppCompatActivity {

    Button btninc,btnoutc;
    LinearLayout laydha,laybar,laycha,laykhu,laymay,layraj,layran,laysyl,lay1111;
    TextView tvdha1,tvdha2,tvbar1,tvbar2,tvctg1,tvctg2,tvkhu1,tvkhu2,tvmay1,tvmay2,tvraj1,tvraj2,tvran1,tvran2,tvsyl1,tvsyl2;
    String link;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_div);

        btninc = findViewById(R.id.btninc);
        btnoutc =findViewById(R.id.btnoutc);

        tvdha1 = findViewById(R.id.tvdha1);
        tvdha2 = findViewById(R.id.tvdha2);
        tvbar1 = findViewById(R.id.tvbar1);
        tvbar2 = findViewById(R.id.tvbar2);
        tvctg1 = findViewById(R.id.tvctg1);
        tvctg2 = findViewById(R.id.tvctg2);
        tvkhu1 = findViewById(R.id.tvkhu1);
        tvkhu2 = findViewById(R.id.tvkhu2);
        tvmay1 = findViewById(R.id.tvmay1);
        tvmay2 = findViewById(R.id.tvmay2);
        tvraj1 = findViewById(R.id.tvraj1);
        tvraj2 = findViewById(R.id.tvraj2);
        tvran1 = findViewById(R.id.tvran1);
        tvran2 = findViewById(R.id.tvran2);
        tvsyl1 = findViewById(R.id.tvsyl1);
        tvsyl2 = findViewById(R.id.tvsyl2);


        laydha = findViewById(R.id.laydha);
        laybar = findViewById(R.id.laybar);
        laycha = findViewById(R.id.laycha);
        laykhu = findViewById(R.id.laykhu);
        laymay = findViewById(R.id.laymay);
        layraj = findViewById(R.id.layraj);
        layran = findViewById(R.id.layran);
        laysyl = findViewById(R.id.laysyl);
        lay1111 = findViewById(R.id.lay1111);

        btninc.setBackgroundResource(R.drawable.btn2);
        btninc.setTextColor(Color.BLACK);
        btnoutc.setBackgroundResource(R.drawable.btn2);
        btnoutc.setTextColor(Color.BLACK);
        builder = new AlertDialog.Builder(this);


        lay1111.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Alert!")
                        .setMessage("Please select one Intra City or Whole Devision")
                        .setCancelable(true)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })

                        .show();
            }
        });


        btninc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btninc.setBackgroundResource(R.drawable.btn);
                btninc.setTextColor(Color.WHITE);
                btnoutc.setBackgroundResource(R.drawable.btn2);
                btnoutc.setTextColor(Color.BLACK);

                tvdha1.setText("Inside Dhaka City");
                tvdha2.setText("Here you can find inside Dhaka city famous visiting spots.");
                tvbar1.setText("Inside Barishal City");
                tvbar2.setText("Here you can find inside Barishal city famous visiting spots.");
                tvctg1.setText("Inside Chittagong City");
                tvctg2.setText("Here you can find inside Chittagong city famous visiting spots.");
                tvkhu1.setText("Inside Khulna City");
                tvkhu2.setText("Here you can find inside Khulna city famous visiting spots.");
                tvmay1.setText("Inside Mymensingh City");
                tvmay2.setText("Here you can find inside Mymensingh city famous visiting spots.");
                tvraj1.setText("Inside Rajshahi City");
                tvraj2.setText("Here you can find inside Rajshahi city famous visiting spots.");
                tvran1.setText("Inside Rangpur City");
                tvran2.setText("Here you can find inside Rangpur city famous visiting spots.");
                tvsyl1.setText("Inside Sylhet City");
                tvsyl2.setText("Here you can find inside Sylhet city famous visiting spots.");


                laydha.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        link = "https://www.tripadvisor.com/Tourism-g293936-Dhaka_City_Dhaka_Division-Vacations.html" ;
                        Intent intent = new Intent(SpotActivity.this,expandSpot.class);
                        intent.putExtra("url", link);
                        startActivity(intent);

                    }
                });

                layran.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        link = "https://www.tripadvisor.com/Tourism-g667999-Rangpur_Rajshahi_Division-Vacations.html";
                        Intent intent = new Intent(SpotActivity.this, expandSpot.class);
                        intent.putExtra("url", link);
                        startActivity(intent);
                    }
                });

                layraj.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        link = "https://www.tripadvisor.com/Tourism-g667998-Rajshahi_City_Rajshahi_Division-Vacations.html";
                        Intent intent = new Intent(SpotActivity.this,expandSpot.class);
                        intent.putExtra("url", link);
                        startActivity(intent);
                    }
                });

                laymay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // link = "https://travelplacebd.com/2022/04/20/mymensingh-tourist-spot-mymensingh-must-visit-place/#google_vignette";
                        link = "https://www.tripadvisor.com/Tourism-g11801509-Mymensingh-Vacations.html";
                        Intent intent = new Intent(SpotActivity.this,expandSpot.class);
                        intent.putExtra("url", link);
                        startActivity(intent);
                    }
                });

                laybar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        link = "https://www.tripadvisor.com/Tourism-g667461-Barisal_City_Barisal_Division-Vacations.html" ;
                        Intent intent = new Intent(SpotActivity.this,expandSpot.class);
                        intent.putExtra("url", link);
                        startActivity(intent);

                    }
                });

                laycha.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        link = "https://www.tripadvisor.com/Tourism-g319837-Chittagong_City_Chittagong_Division-Vacations.html";
                        Intent intent = new Intent(SpotActivity.this, expandSpot.class);
                        intent.putExtra("url", link);
                        startActivity(intent);
                    }
                });

                laysyl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        link = "https://www.tripadvisor.com/Tourism-g667997-Sylhet_City_Sylhet_Division-Vacations.html";
                        Intent intent = new Intent(SpotActivity.this,expandSpot.class);
                        intent.putExtra("url", link);
                        startActivity(intent);
                    }
                });

                laykhu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        link = "https://www.tripadvisor.com/Tourism-g667472-Khulna_City_Khulna_Division-Vacations.html";
                        Intent intent = new Intent(SpotActivity.this,expandSpot.class);
                        intent.putExtra("url", link);
                        startActivity(intent);
                    }
                });
            }
        });


        btnoutc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnoutc.setBackgroundResource(R.drawable.btn);
                btnoutc.setTextColor(Color.WHITE);
                btninc.setBackgroundResource(R.drawable.btn2);
                btninc.setTextColor(Color.BLACK);


                tvdha1.setText("Whole Dhaka Division");
                tvdha2.setText("Here you can find Whole Dhaka Division famous visiting spots.");
                tvbar1.setText("Whole Barishal Division");
                tvbar2.setText("Here you can find Whole Barishal Division famous visiting spots.");
                tvctg1.setText("Whole Chittagong Division");
                tvctg2.setText("Here you can find Whole Chittagong Division famous visiting spots.");
                tvkhu1.setText("Whole Khulna Division");
                tvkhu2.setText("Here you can find Whole Khulna Division famous visiting spots.");
                tvmay1.setText("Whole Mymensingh Division");
                tvmay2.setText("Here you can find Whole Mymensingh Division famous visiting spots.");
                tvraj1.setText("Whole Rajshahi Division");
                tvraj2.setText("Here you can find Whole Rajshahi Division famous visiting spots.");
                tvran1.setText("Whole Rangpur Division");
                tvran2.setText("Here you can find Whole Rangpur Division famous visiting spots.");
                tvsyl1.setText("Whole Sylhet Division");
                tvsyl2.setText("Here you can find Whole Sylhet Division famous visiting spots.");


                laydha.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        link = "https://www.tripadvisor.com/Tourism-g667479-Dhaka_Division-Vacations.html" ;
                        Intent intent = new Intent(SpotActivity.this,expandSpot.class);
                        intent.putExtra("url", link);
                        startActivity(intent);

                    }
                });

                layran.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        link = "https://www.touristplaces.com.bd/rangpur-division/";
                        Intent intent = new Intent(SpotActivity.this, expandSpot.class);
                        intent.putExtra("url", link);
                        startActivity(intent);
                    }
                });

                layraj.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        link = "https://www.tripadvisor.com/Tourism-g667482-Rajshahi_Division-Vacations.html";
                        Intent intent = new Intent(SpotActivity.this,expandSpot.class);
                        intent.putExtra("url", link);
                        startActivity(intent);
                    }
                });

                laymay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // link = "https://travelplacebd.com/2022/04/20/mymensingh-tourist-spot-mymensingh-must-visit-place/#google_vignette";
                        link = "https://travelplacebd.com/2022/04/20/mymensingh-tourist-spot-mymensingh-must-visit-place/";
                        Intent intent = new Intent(SpotActivity.this,expandSpot.class);
                        intent.putExtra("url", link);
                        startActivity(intent);
                    }
                });

                laybar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        link = "https://www.tripadvisor.com/Tourism-g667484-Barisal_Division-Vacations.html" ;
                        Intent intent = new Intent(SpotActivity.this,expandSpot.class);
                        intent.putExtra("url", link);
                        startActivity(intent);

                    }
                });

                laycha.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        link = "https://www.tripadvisor.com/Tourism-g667480-Chittagong_Division-Vacations.html";
                        Intent intent = new Intent(SpotActivity.this, expandSpot.class);
                        intent.putExtra("url", link);
                        startActivity(intent);
                    }
                });

                laysyl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        link = "https://www.tripadvisor.com/Tourism-g667483-Sylhet_Division-Vacations.html";
                        Intent intent = new Intent(SpotActivity.this,expandSpot.class);
                        intent.putExtra("url", link);
                        startActivity(intent);
                    }
                });

                laykhu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        link = "https://www.tripadvisor.com/Tourism-g667481-Khulna_Division-Vacations.html";
                        Intent intent = new Intent(SpotActivity.this,expandSpot.class);
                        intent.putExtra("url", link);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}