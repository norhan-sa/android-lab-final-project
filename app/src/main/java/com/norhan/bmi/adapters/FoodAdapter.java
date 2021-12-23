package com.norhan.bmi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.norhan.bmi.R;
import com.norhan.bmi.models.Food;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<Food> mFoods;

    public FoodAdapter(List<Food> foods) {
        mFoods = foods;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View foodView = inflater.inflate(R.layout.food_item, parent, false);

        return new FoodViewHolder(foodView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = mFoods.get(position);

        ImageView foodImageView = holder.foodImage;
        foodImageView.setImageURI(food.getImage());

        TextView foodNameTextView = holder.nameTextView;
        foodNameTextView.setText(food.getName());

        TextView foodCategoryTextView = holder.categoryTextView;
        foodCategoryTextView.setText(food.getCategory());

        TextView foodCaloriesTextView = holder.caloriesTextView;
        foodCaloriesTextView.setText(food.getCalories() + "");
    }

    @Override
    public int getItemCount() {
        return mFoods.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        public ImageView foodImage;
        public Button editButton, deleteButton;
        public TextView nameTextView, categoryTextView, caloriesTextView;

        public FoodViewHolder(View itemView) {
            super(itemView);

            foodImage = itemView.findViewById(R.id.food_image_view);
            editButton = itemView.findViewById(R.id.food_edit_button);
            deleteButton = itemView.findViewById(R.id.food_delete_button);
            nameTextView = itemView.findViewById(R.id.food_name);
            categoryTextView = itemView.findViewById(R.id.food_category);
            caloriesTextView = itemView.findViewById(R.id.food_calories);
        }
    }
}