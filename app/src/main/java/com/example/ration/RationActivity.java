package com.example.ration;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ration.model.NormalRecipe;
import com.example.ration.model.RecipeType;
import com.example.ration.model.User;
import com.example.ration.model.Week;
import com.example.ration.service.RestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class RationActivity extends AppCompatActivity {
    private final Map<Week, Map<RecipeType, NormalRecipe>> recipes = new HashMap<>();
    private static final String FILE_NAME = "recipes.txt";

    {
        try(ObjectInputStream os = new ObjectInputStream(this.openFileInput(FILE_NAME))) {
            recipes.putAll((Map<Week, Map<RecipeType, NormalRecipe>>) os.readObject());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ration);

        User user = new User(getIntent().getParcelableExtra("user"),
                getIntent().getStringExtra("gender"),
                getIntent().getStringExtra("activity"));

        System.out.println(user);

        Button generateRationButton = findViewById(R.id.generateRationButton);
        Button getRecipesButton = findViewById(R.id.getWeekReceipsButton);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        generateRationButton.setOnClickListener(v -> {
            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET,
                    RestService.URL,
                    null,
                    this::serializeAndSave,
                    e -> System.out.println(e.toString()));
            requestQueue.add(objectRequest);
        });

        getRecipesButton.setOnClickListener(v -> System.out.println(recipes));

    }

    private void serializeAndSave(JSONObject readValue) {

        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<HashMap<Week, Map<RecipeType, NormalRecipe>>> typeRef = new TypeReference<>() {};

        try {
            recipes.putAll(objectMapper.readValue(readValue.toString(), typeRef));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        try (ObjectOutputStream os = new ObjectOutputStream(this.openFileOutput(FILE_NAME, Context.MODE_PRIVATE))) {
            os.writeObject(recipes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}