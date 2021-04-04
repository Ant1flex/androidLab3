package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button lengthBtn = findViewById(R.id.lengthBtn);
        lengthBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, LengthActivity.class);
            startActivity(intent);
        });

        final Button weightBtn = findViewById(R.id.weightBtn);
        weightBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, WeightActivity.class);
            startActivity(intent);
        });

        final Button temperatureBtn = findViewById(R.id.temperatureBtn);
        temperatureBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, TemperatureActivity.class);
            startActivity(intent);
        });
    }
}