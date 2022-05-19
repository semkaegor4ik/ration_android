package com.example.ration;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ration.model.NormalRecipe;
import com.example.ration.util.RationUtil;

import java.util.Map;
import java.util.stream.Collectors;

public class WeekRation extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_ration);

        TextView nameText = findViewById(R.id.name);
        TextView descriptionText = findViewById(R.id.description);
        TextView productsText = findViewById(R.id.products);

        ImageView imageView = findViewById(R.id.imageView);

        NormalRecipe recipe = (NormalRecipe) getIntent().getSerializableExtra("recipe");

        byte[] decodedImage = Base64.decode(recipe.getEncodedView(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
        imageView.setImageBitmap(decodedByte);

        nameText.setText(recipe.getName());
        descriptionText.setText(recipe.getDescription());
        productsText.setText(RationUtil.getProductsText(recipe.getProducts()));
    }
}