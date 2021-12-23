package com.norhan.bmi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button loginButton;

    private FirebaseAuth mAuth;

    private EditText email;
    private TextInputLayout password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Login");
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.activity_center_title);

        loginButton = findViewById(R.id.login_login_button);
        loginButton.setOnClickListener(this);
        email = findViewById(R.id.emailTextFieldId);
        password = findViewById(R.id.passTextFieldId);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            navigateToHomeScreen();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_button) {
            mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getEditText().getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("LOGIN", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                SharedPreferences preferences = getSharedPreferences("BMI", Context.MODE_MULTI_PROCESS);;
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("uid", user.getUid());
                                editor.putString("name", user.getDisplayName());
                                editor.commit();
                                navigateToHomeScreen();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("LOGIN", "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
//                                updateUI(null);
                            }
                        }
                    });
        } else if (v.getId() == R.id.login_login_button) {
            Intent activityChangeIntent = new Intent(this, RegistrationActivity.class);
            this.startActivity(activityChangeIntent);
        }
    }

    public void navigateToHomeScreen() {
        Intent activityChangeIntent = new Intent(this, HomeActivity.class);
        this.startActivity(activityChangeIntent);
    }
}