package com.example.ration.util;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RationUtil {
    private static final String FORMAT = "%s - %s грамм(а)";

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getProductsText(Map<String, Integer> products) {
        return products.entrySet().stream()
                .map(entry -> String.format(FORMAT, entry.getKey(), entry.getValue()))
                .collect(Collectors.joining("\n"));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void createNewActivity(Map<String, Serializable> extras, Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        extras.forEach(intent::putExtra);
        context.startActivity(intent);
    }
}
