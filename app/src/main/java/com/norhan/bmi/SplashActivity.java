package com.norhan.bmi;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {
    Button splashButton;

    final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        splashButton = findViewById(R.id.splashNextButtonId);
        splashButton.setOnClickListener(this);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                navigateToLogin();
            }
        }, 5000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onClick(View v) {
        navigateToLogin();
    }

    public void navigateToLogin() {
        Intent activityChangeIntent = new Intent(this, LoginActivity.class);
        this.startActivity(activityChangeIntent);
    }
}