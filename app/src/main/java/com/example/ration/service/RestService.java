package com.example.ration.service;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ration.model.NormalRecipe;
import com.example.ration.model.RecipeType;
import com.example.ration.model.User;
import com.example.ration.model.Week;

import java.util.Map;

public class RestService {

    public static final String URL = "https://api.weatherapi.com/v1/current.json?key=c9dd91bd98c644f4851173437221705&q=London&aqi=no";//"localhost8075/ration";

    public Map<Week, Map<RecipeType, NormalRecipe>> getRecipes(User user, Context context) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET,
                URL,
                null,
                o -> System.out.println(o.toString()),
                e -> System.out.println(e.toString()));
        requestQueue.add(objectRequest);
        System.out.println("hello");
        return null;
    }
}
