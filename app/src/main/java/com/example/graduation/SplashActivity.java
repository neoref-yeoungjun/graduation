package com.example.graduation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DP_TIME = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceStare) {

        super.onCreate(savedInstanceStare);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DP_TIME);
    }
    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
}
