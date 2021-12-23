package com.norhan.bmi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.norhan.bmi.adapters.FoodAdapter;
import com.norhan.bmi.models.Food;

import java.util.ArrayList;

public class FoodList extends AppCompatActivity {

    ArrayList<Food> foods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        RecyclerView rvFoods = findViewById(R.id.foods);

        foods = Food.createContactsList(20);
        FoodAdapter adapter = new FoodAdapter(foods);
        rvFoods.setAdapter(adapter);
        rvFoods.setLayoutManager(new LinearLayoutManager(this));
    }
}