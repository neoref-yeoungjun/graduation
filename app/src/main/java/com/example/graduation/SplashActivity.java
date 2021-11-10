package com.example.graduation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DP_TIME = 5000;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();// 로그인 상태확인
    @Override
    protected void onCreate(Bundle savedInstanceStare) {

        super.onCreate(savedInstanceStare);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    if(user != null ){
                        if(user.getUid().equals("EV2I7N1QRtMRWkpzDbOeKnnJB2H2")) {
                            intent = new Intent(getApplicationContext(), adminActivity.class);
                        }
                    }
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
