package com.norhan.bmi.models;

import java.util.ArrayList;

public class Status {
    private String mDate;
    private double mWeight;
    private double mHeight;
    private boolean mIsNormal;

    public Status() {}

    public Status(String date, double weight, double height, boolean isNormal) {
        mDate = date;
        mWeight = weight;
        mHeight = height;
        mIsNormal = isNormal;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public double getmWeight() {
        return mWeight;
    }

    public void setmWeight(double mWeight) {
        this.mWeight = mWeight;
    }

    public double getmHeight() {
        return mHeight;
    }

    public void setmHeight(double mHeight) {
        this.mHeight = mHeight;
    }

    public boolean isNormal() {
        return mIsNormal;
    }

    public void setNormal(boolean mIsNormal) {
        this.mIsNormal = mIsNormal;
    }

    public static ArrayList<Status> createContactsList(int numContacts) {
        ArrayList<Status> contacts = new ArrayList<Status>();

        for (int i = 1; i <= numContacts; i++) {
            contacts.add(new Status("12/12/2021", 70.2, 177, true));
        }

        return contacts;
    }
}