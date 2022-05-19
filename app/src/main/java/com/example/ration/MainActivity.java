package com.example.ration;

import android.content.Context;
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

import com.example.ration.model.Activity;
import com.example.ration.model.Gender;
import com.example.ration.model.UserData;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MainActivity extends AppCompatActivity {
    private UserData userData;

    private static final String FILE_NAME = "user.ser";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try (ObjectInputStream os = new ObjectInputStream(this.openFileInput(FILE_NAME))) {
            userData = (UserData) os.readObject();
            createNewActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }

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

                userData = new UserData(name.getText().toString(),
                        Integer.parseInt(weight.getText().toString()),
                        Integer.parseInt(height.getText().toString()),
                        Integer.parseInt(age.getText().toString()),
                        Gender.getGender(gender),
                        Activity.getActivity(activity));

                try (ObjectOutputStream os = new ObjectOutputStream(this.openFileOutput(new File(FILE_NAME).getName(), Context.MODE_PRIVATE))) {
                    os.writeObject(userData);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                createNewActivity();
            } catch (Exception e) {
                Toast.makeText(this, "uncorrect data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createNewActivity() {
        Intent intent = new Intent(this, RationActivity.class);
        intent.putExtra("user", userData);

        startActivity(intent);
    }
}