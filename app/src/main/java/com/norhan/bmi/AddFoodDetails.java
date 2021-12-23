package com.norhan.bmi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class AddFoodDetails extends AppCompatActivity implements View.OnClickListener {
    public static final int PICK_IMAGE = 1;

    ImageView foodImage;

    FirebaseStorage storage = FirebaseStorage.getInstance();

    StorageReference storageRef = storage.getReference();

    EditText foodName;
    EditText foodCal;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_details);

        foodImage = findViewById(R.id.food_image_view);
        foodName = findViewById(R.id.food_name);
        foodCal = findViewById(R.id.food_calories);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.upload_image_button) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        } else if (view.getId() == R.id.save_food_details) {
            FirebaseAuth auth = FirebaseAuth.getInstance();

            StorageReference mountainsRef = storageRef.child(auth.getCurrentUser().getUid() + "/food/" + foodName.getText().toString());

            foodImage.setDrawingCacheEnabled(true);
            foodImage.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) foodImage.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = mountainsRef.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Map<String, Object> bmiRecord = new HashMap<>();
                    bmiRecord.put("uid", auth.getUid());
                    bmiRecord.put("name", foodName.getText().toString());
                    bmiRecord.put("category", "");
                    bmiRecord.put("cal", foodCal.getText().toString());
                    bmiRecord.put("photo", taskSnapshot.getUploadSessionUri().toString());

                    System.out.println("bmiRecord: " + bmiRecord.toString());

                    saveData(bmiRecord);
                }
            });
        }
    }

    void saveData(Map<String, Object> bmiRecord) {
        db.collection("food")
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            foodImage.setImageURI(data.getData());
        }
    }
}