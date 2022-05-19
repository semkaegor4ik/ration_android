package com.example.ration;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.ration.model.NormalRecipe;
import com.example.ration.model.RecipeType;
import com.example.ration.model.UserData;
import com.example.ration.model.Week;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RationActivity extends AppCompatActivity {
    private final Map<Week, Map<RecipeType, NormalRecipe>> recipes = new HashMap<>();
    private static final String FILE_NAME = "recipes.ser";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ration);

        try (ObjectInputStream os = new ObjectInputStream(this.openFileInput(FILE_NAME))) {
            recipes.putAll((Map<Week, Map<RecipeType, NormalRecipe>>) os.readObject());
        } catch (Exception e) {
            e.printStackTrace();
        }

        UserData userData = (UserData) getIntent().getSerializableExtra("user");

        Button generateRationButton = findViewById(R.id.generateRationButton);
        Button getRecipesButton = findViewById(R.id.getWeekReceipsButton);
        Button getProductListButton = findViewById(R.id.getProductListButton);

        RadioGroup mealRadioGroup = findViewById(R.id.meal);
        RadioGroup weekRadioGroup = findViewById(R.id.weeks);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        generateRationButton.setOnClickListener(v -> serializeAndSave(null)/*{
            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET,
                    RestService.URL,
                    null,
                    this::serializeAndSave,
                    e -> System.out.println(e.toString()));
            requestQueue.add(objectRequest);
        }*/);

        getRecipesButton.setOnClickListener(v -> {
            NormalRecipe normalRecipe = recipes.get(Week.valueOf(((RadioButton) findViewById(weekRadioGroup.getCheckedRadioButtonId())).getText().toString().toUpperCase()))
                    .get(RecipeType.valueOf((((RadioButton) findViewById(mealRadioGroup.getCheckedRadioButtonId())).getText().toString().toUpperCase())));

            Intent intent = new Intent(this, WeekRation.class);
            intent.putExtra("recipe", normalRecipe);

            startActivity(intent);
        });

        getProductListButton.setOnClickListener(v -> {
            HashMap<String, Integer> list = new HashMap<>();
            recipes.values().stream()
                    .map(Map::values)
                    .flatMap(Collection::stream)
                    .map(NormalRecipe::getProducts)
                    .map(Map::entrySet)
                    .flatMap(Collection::stream)
                    .forEach(entry -> {
                        if (list.get(entry.getKey()) == null) {
                            list.put(entry.getKey(), entry.getValue());
                        } else {
                            list.put(entry.getKey(), list.get(entry.getKey()) + entry.getValue());
                        }
                    });

            Intent intent = new Intent(this, WeekProductList.class);
            intent.putExtra("productList", list);

            startActivity(intent);
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void serializeAndSave(JSONObject readValue) {

        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<HashMap<Week, Map<RecipeType, NormalRecipe>>> typeRef = new TypeReference<>() {
        };

        try (InputStream is = getAssets().open("response.json")) {
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);

            String response = new String(buffer);
            recipes.putAll(objectMapper.readValue(response, typeRef));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (ObjectOutputStream os = new ObjectOutputStream(this.openFileOutput(new File(FILE_NAME).getName(), Context.MODE_PRIVATE))) {
            os.writeObject(recipes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}