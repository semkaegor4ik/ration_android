package com.example.ration;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ration.util.RationUtil;

import java.util.Collections;

public class StartActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button start = findViewById(R.id.start);
        start.setOnClickListener(ignored -> RationUtil.createNewActivity(Collections.EMPTY_MAP, this, UserInfoActivity.class));
    }
}