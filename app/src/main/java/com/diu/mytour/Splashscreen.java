package com.diu.mytour;

import android.os.Bundle;

public class Splashscreen extends android.app.Activity {
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        android.view.Window window = getWindow();
        window.setFormat(android.graphics.PixelFormat.RGBA_8888);
    }

    Thread splashTread;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        StartAnimations();
    }
    private void StartAnimations() {

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;

                    while (waited < 3500) {
                        sleep(100);
                        waited += 100;
                    }
                    android.content.Intent intent = new android.content.Intent(Splashscreen.this,
                            LogIn.class);
                    intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    Splashscreen.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    Splashscreen.this.finish();
                }

            }
        };
        splashTread.start();

    }
}

