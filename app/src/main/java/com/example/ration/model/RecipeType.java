package com.example.ration.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Arrays;
import java.util.Objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RecipeType {
    BREAKFAST("завтрак"),
    LUNCH("обед"),
    DINNER("ужин");
    private final String buttonName;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static RecipeType getRecipeType(String recipeType) {
        return Arrays.stream(RecipeType.values())
                .filter(g -> Objects.equals(g.getButtonName(), recipeType))
                .findFirst()
                .get();
    }
}
