package com.norhan.bmi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FinishRegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_registration);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.save_data_button) {
            Intent activityChangeIntent = new Intent(this, HomeActivity.class);
            this.startActivity(activityChangeIntent);
        }
    }
}