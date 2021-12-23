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
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;

    private EditText email;
    private EditText nameField;
    private TextInputLayout password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("SignUp");
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.activity_center_title);

        email = findViewById(R.id.emailTextFieldId);
        nameField = findViewById(R.id.name_field);
        password = findViewById(R.id.passTextFieldId);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.create_button) {
            System.out.println("email.getText().toString(): " + email.getText().toString());
            mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getEditText().getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("SIGN_UP", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();

                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(nameField.getText().toString()).build();

                                user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        SharedPreferences preferences = getSharedPreferences("BMI", Context.MODE_MULTI_PROCESS);;
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putString("uid", user.getUid());
                                        editor.putString("name", nameField.getText().toString());
                                        editor.commit();

                                        navigateToFinishRegistration();
                                    }
                                });
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("SIGN_UP", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegistrationActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
//                                updateUI(null);
                            }
                        }
                    });
        } else if (v.getId() == R.id.sign_up_button) {
            finish();
        }
    }

    public void navigateToFinishRegistration() {
        Intent activityChangeIntent = new Intent(this, FinishRegistrationActivity.class);
        this.startActivity(activityChangeIntent);
    }
}