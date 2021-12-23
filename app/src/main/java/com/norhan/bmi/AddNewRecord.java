package com.norhan.bmi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddNewRecord extends AppCompatActivity implements View.OnClickListener {

    private TextView weightValue, lengthValue;
    private EditText date, time;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_record);

        weightValue = findViewById(R.id.weight_value);
        lengthValue = findViewById(R.id.length_value);
        date = findViewById(R.id.date_value);
        time = findViewById(R.id.time_value);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.increase_weight) {
            weightValue.setText((Integer.parseInt(weightValue.getText().toString()) + 1) + "");
        } else if (view.getId() == R.id.decrease_weight) {
            int currentWeight = Integer.parseInt(weightValue.getText().toString());
            if (currentWeight > 1) {
                weightValue.setText((currentWeight - 1) + "");
            }
        } else if (view.getId() == R.id.increase_length) {
            lengthValue.setText(Integer.parseInt(lengthValue.getText().toString()) + 1 + "");
        } else if (view.getId() == R.id.decrease_length) {
            int currentLength = Integer.parseInt(lengthValue.getText().toString());
            if (currentLength > 1) {
                lengthValue.setText((currentLength - 1) + "");
            }
        } else if (view.getId() == R.id.save_new_record) {
            FirebaseAuth auth = FirebaseAuth.getInstance();

            Map<String, Object> bmiRecord = new HashMap<>();
            bmiRecord.put("uid", auth.getUid());
            bmiRecord.put("weight", weightValue.getText().toString());
            bmiRecord.put("length", lengthValue.getText().toString());
            bmiRecord.put("date", date.getText().toString());
            bmiRecord.put("time", time.getText().toString());

            db.collection("bmi_records")
                    .add(bmiRecord)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("Add new record", "DocumentSnapshot added with ID: " + documentReference.getId());
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("Add new record", "Error adding document", e);
                        }
                    });
        }
    }
}