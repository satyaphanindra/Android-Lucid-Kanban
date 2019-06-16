package com.citta.lucidkanban.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.citta.lucidkanban.R;

public class SplashScreenActivity extends AppCompatActivity {

    private int splash_Timeout = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent= new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        }, splash_Timeout);
    }

}
