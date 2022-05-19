package com.example.ration.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserData implements Serializable {
    private final String name;
    private final int weight;
    private final int height;
    private final int age;
    private final Gender gender;
    private final Activity activity;
}
