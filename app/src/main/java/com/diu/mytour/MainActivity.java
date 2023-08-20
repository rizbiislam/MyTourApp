package com.diu.mytour;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_LOCATION = 1;
    private FusedLocationProviderClient fusedLocationClient;
    private String currentDateTimeString;
    private String latitude, longitude, link;
    private RequestQueue mRequestQue;
    private Intent intent;
    AlertDialog.Builder builder;
    private String URL = "https://fcm.googleapis.com/fcm/send";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        builder = new AlertDialog.Builder(this);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            // Location retrieval success
                            double lat = location.getLatitude();
                            double longi = location.getLongitude();
                            latitude = String.valueOf(lat);
                            longitude = String.valueOf(longi);
                            link = "http://www.google.com/maps/place/" + latitude + "," + longitude;
                            // Proceed with the retrieved location
                        } else {
                            // Location is null, handle accordingly
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Location retrieval failed, handle the exception
                    }
                });

        currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());

        if (getIntent().hasExtra("time")) {
            Intent intent = new Intent(MainActivity.this, ReceiveNotificationActivity.class);
            intent.putExtra("time", getIntent().getStringExtra("time"));
            intent.putExtra("link", getIntent().getStringExtra("link"));
            startActivity(intent);
        }

        Button set = findViewById(R.id.set);
        Button plans = findViewById(R.id.plans);
        Button ticket = findViewById(R.id.ticket);
        Button guide = findViewById(R.id.guide);
        Button spot = findViewById(R.id.spot);
        Button news = findViewById(R.id.news);
        Button sos = findViewById(R.id.sos);
        Button weather = findViewById(R.id.weather);
        Button logout = findViewById(R.id.logout);
        mRequestQue = Volley.newRequestQueue(this);
        FirebaseMessaging.getInstance().subscribeToTopic("news");

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SetPlanActivity.class);
                startActivity(intent);
            }
        });

        plans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean statuses = true; // Use the primitive boolean type

                Intent intent = new Intent(MainActivity.this, TourPlanActivity.class);

                intent.putExtra("STATUS_KEY", statuses); // Pass the status data with the intent
                startActivity(intent);
            }
        });


        ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TicketActivity.class);
                startActivity(intent);
            }
        });

        spot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SpotActivity.class);
                startActivity(intent);
            }
        });

        guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Hire Guide")
                        .setMessage("select a way to hire guide")
                        .setCancelable(true)
                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .setNegativeButton("By Website", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                String link1 = "https://www.helloreco.com/?utm_source=tripadvisor&utm_medium=home&utm_campaign=ql&variant=hire_a_td&location_id=1";
                                Intent intent = new Intent(MainActivity.this, expandSpot.class);
                                intent.putExtra("url", link1);
                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("By Call", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:01712345678"));
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        });

        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewspaperActivity.class);
                startActivity(intent);
            }
        });

        sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Logout !")
                        .setMessage("Are you sure Logout!")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SharedPreferences sharedPreferences = getSharedPreferences("checked", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("remember", "false");
                                editor.apply();
                                FirebaseAuth.getInstance().signOut();
                                Intent orderIntent = new Intent(MainActivity.this, LogIn.class);
                                orderIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(orderIntent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();
            }
        });

        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link1 = "https://weather.com/weather/today" ;
                Intent intent = new Intent(MainActivity.this,expandSpot.class);
                intent.putExtra("url", link1);
                startActivity(intent);
            }
        });
    }


    private void sendNotification() {

        JSONObject json = new JSONObject();
        try {
            json.put("to", "/topics/" + "news");
            JSONObject notificationObj = new JSONObject();
            notificationObj.put("title", "Emergency Help!");
            notificationObj.put("body", "Please safe me if you're nearby.");

            JSONObject extraData = new JSONObject();
            extraData.put("time", currentDateTimeString);
            extraData.put("link", link);



            json.put("notification",notificationObj);
            json.put("data",extraData);


            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL,
                    json,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.d("MUR", "onResponse: ");
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("MUR", "onError: "+error.networkResponse);
                }
            }
            ){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> header = new HashMap<>();
                    header.put("content-type","application/json");
                    header.put("authorization","key=AAAA2jbYH6w:APA91bHujxoBomkKxU_nHtDawaGBGeO5J-j7wUF5b4a-KKqUvQVSucD5X5uXPhTi3qBkFysePX1NUwhWtIWMhCwoapVqWuq2m_0yWM3-JWJw6oeUtJSy9DFve9HULNxSy1POGFej1xTb");
                    return header;
                }
            };
            mRequestQue.add(request);
        }
        catch (JSONException e)

        {
            e.printStackTrace();
        }
    }

}
