package com.example.ration;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.example.ration.model.NormalRecipe;
import com.example.ration.util.RationUtil;

import java.util.HashMap;

public class WeekProductList extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_product_list);

        TextView productList = findViewById(R.id.productList);

        HashMap<String, Integer> list = (HashMap<String, Integer>) getIntent().getSerializableExtra("productList");

        productList.setText(RationUtil.getProductsText(list));
    }
}