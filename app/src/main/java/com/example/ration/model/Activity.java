package com.example.ration.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Arrays;
import java.util.Objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Activity {
    MINIMUM("минимальная"),
    LOW("низкая"),
    NORMAL("средняя"),
    HIGH("выше среднего"),
    EXTRA("высокая");

    private final String buttonName;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Activity getActivity(String activity) {
        return Arrays.stream(Activity.values())
                .filter(a -> Objects.equals(activity, a.buttonName))
                .findFirst()
                .get();
    }
}
