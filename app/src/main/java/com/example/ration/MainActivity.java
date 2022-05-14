package com.example.ration;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ration.model.ParcelableUser;

public class MainActivity extends AppCompatActivity {
    private ParcelableUser parcelableUser;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button registerButton = findViewById(R.id.registerButton);

        RadioGroup genderRadioGroup = findViewById(R.id.gender);
        RadioGroup activityRadioGroup = findViewById(R.id.activity);

        TextView name = findViewById(R.id.name);
        TextView weight = findViewById(R.id.weight);
        TextView height = findViewById(R.id.height);
        TextView age = findViewById(R.id.age);

        registerButton.setOnClickListener(v -> {
            try {
                String gender = ((RadioButton) findViewById(genderRadioGroup.getCheckedRadioButtonId())).getText().toString();
                String activity = ((RadioButton) findViewById(activityRadioGroup.getCheckedRadioButtonId())).getText().toString();

                parcelableUser = new ParcelableUser(name.getText().toString(),
                        Integer.parseInt(weight.getText().toString()),
                        Integer.parseInt(height.getText().toString()),
                        Integer.parseInt(age.getText().toString()));

                Intent intent = new Intent(this, RationActivity.class);
                intent.putExtra("user", parcelableUser);
                intent.putExtra("activity", activity);
                intent.putExtra("gender", gender);

                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(this, "uncorrect data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}