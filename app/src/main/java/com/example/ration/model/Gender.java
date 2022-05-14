package com.example.ration.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.util.Arrays;
import java.util.Objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Getter
public enum Gender {
    MALE("male"),
    FEMALE("female");
    private final String buttonName;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Gender getGender(String gender) {
        return Arrays.stream(Gender.values())
                .filter(g -> Objects.equals(gender, g.buttonName))
                .findFirst()
                .get();
    }
}
