package com.norhan.bmi.models;

import android.net.Uri;

import java.util.ArrayList;

public class Food {
    private int mId;
    private String mName, mCategory;
    private double mCalories;
    private Uri mImage;

    public Food(int id, String name, String category, double calories, Uri image) {
        mId = id;
        mName = name;
        mCategory = category;
        mCalories = calories;
        mImage = image;
    }

    public Food(int id, String name, String category, double calories) {
        mId = id;
        mName = name;
        mCategory = category;
        mCalories = calories;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mDate) {
        this.mName = mDate;
    }

    public double getCalories() {
        return mCalories;
    }

    public void setCalories(double mWeight) {
        this.mCalories = mWeight;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public Uri getImage() {
        return mImage;
    }

    public void setImage(Uri mImage) {
        this.mImage = mImage;
    }

    public static ArrayList<Food> createContactsList(int numContacts) {
        ArrayList<Food> contacts = new ArrayList<Food>();

        for (int i = 1; i <= numContacts; i++) {
            contacts.add(new Food(i, "Food #" + i, "Category", 12.5));
        }

        return contacts;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }
}