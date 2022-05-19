package com.example.ration.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Arrays;
import java.util.Objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Week {
    SUNDAY("воскресенье"),
    MONDAY("понедельник"),
    TUESDAY("вторник"),
    WEDNESDAY("среда"),
    THURSDAY("четверг"),
    FRIDAY("пятница"),
    SATURDAY("суббота");
    private final String buttonName;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Week getWeek(String week) {
        return Arrays.stream(Week.values())
                .filter(g -> Objects.equals(g.getButtonName(), week))
                .findFirst()
                .get();
    }
}
