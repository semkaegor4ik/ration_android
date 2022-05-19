package com.example.ration.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Arrays;
import java.util.Objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Getter
public enum Gender {
    MALE("мужчина"),
    FEMALE("женщина");
    private final String buttonName;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Gender getGender(String gender) {
        return Arrays.stream(Gender.values())
                .filter(g -> Objects.equals(g.getButtonName(), gender))
                .findFirst()
                .get();
    }
}
