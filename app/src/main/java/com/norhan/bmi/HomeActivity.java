package com.norhan.bmi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.norhan.bmi.adapters.StatusAdapter;
import com.norhan.bmi.models.Status;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;

    ArrayList<Status> statuses = new ArrayList<>();

    protected TextView greetingText;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        RecyclerView rvStatuses = findViewById(R.id.statuses);

//        statuses = Status.createContactsList(20);

        greetingText = findViewById(R.id.greeting_text);

        SharedPreferences preferences = getSharedPreferences("BMI", Context.MODE_PRIVATE);
        ;
        greetingText.append(preferences.getString("name", ""));

        mAuth = FirebaseAuth.getInstance();

        db.collection("bmi_records")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("bmi_records", document.getId() + " => " + document.getData());
                                System.out.println("document.getData()" + document.getData().get("date").toString());
                                statuses.add(
                                        new Status(
                                                (String) document.getData().get("date"),
                                                Double.parseDouble((String) document.getData().get("weight")),
                                                Double.parseDouble((String) document.getData().get("length")),
                                                true
                                        ));
                            }

                            StatusAdapter adapter = new StatusAdapter(statuses);
                            rvStatuses.setAdapter(adapter);
                            rvStatuses.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
                        } else {
                            Log.w("bmi_records", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.logout_button) {
            mAuth.signOut();
            finish();
        } else if (view.getId() == R.id.add_new_record_button) {
            Intent activityChangeIntent = new Intent(this, AddNewRecord.class);
            this.startActivity(activityChangeIntent);
        } else if (view.getId() == R.id.add_new_food_button) {
            Intent activityChangeIntent = new Intent(this, AddFoodDetails.class);
            this.startActivity(activityChangeIntent);
        } else if (view.getId() == R.id.view_food_button) {
            Intent activityChangeIntent = new Intent(this, FoodList.class);
            this.startActivity(activityChangeIntent);
        }
    }
}