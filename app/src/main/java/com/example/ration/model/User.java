package com.example.ration.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import lombok.Data;

@Data
public class User {
    private final String name;
    private final int weight;
    private final int height;
    private final int age;
    private final Gender gender;
    private final Activity activity;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public User(ParcelableUser parcelableUser, String gender, String activity) {
        name = parcelableUser.getName();
        weight = parcelableUser.getWeight();
        height = parcelableUser.getHeight();
        age = parcelableUser.getAge();
        this.gender = Gender.getGender(gender);
        this.activity = Activity.getActivity(activity);
    }
}
