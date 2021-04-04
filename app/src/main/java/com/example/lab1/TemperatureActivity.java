package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TemperatureActivity extends AppCompatActivity implements RetainFragment.StateListener{
    private static final float MINIMUM_CELSIUS = -273.15f;
    private static final float MINIMUM_KELVIN = 0;
    private static final float MINIMUM_FAHRENHEIT = -459.67f;

    private RetainFragment retainFragment;

    Map<String, Float> temperatures = new HashMap<String, Float>();

    Spinner spinnerInput;
    Spinner spinnerOutput;
    EditText input;
    EditText output;
    Button calcBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        retainFragment = (RetainFragment) getSupportFragmentManager().findFragmentByTag(RetainFragment.TAG);
        if(retainFragment==null){
            retainFragment = new RetainFragment();
            getSupportFragmentManager().beginTransaction().add(retainFragment, RetainFragment.TAG).commit();
        }

        spinnerInput = findViewById(R.id.temperatureInputList);
        spinnerOutput = findViewById(R.id.temperatureOutputList);
        input = findViewById(R.id.temperatureInput);
        output = findViewById(R.id.temperatureOutput);
        calcBtn = findViewById(R.id.calcTemperatureBtn);

        initList();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>(temperatures.keySet()));

        spinnerInput.setAdapter(adapter);
        spinnerOutput.setAdapter(adapter);



        calcBtn.setOnClickListener(v -> {
            float value = retainFragment.convertTemp(Float.parseFloat(input.getText().toString()), spinnerInput.getSelectedItem().toString(), spinnerOutput.getSelectedItem().toString());
            if (value != -1000) {
                output.setText(Float.toString(value));
            }
        });
    }

    public float temp(float value, String tempIn, String tempOut) {
        switch (tempIn) {
            case "Celsius": {
                if (value >= MINIMUM_CELSIUS) {
                    if (tempOut.equals("Kelvin"))
                        return value + 273.15f;
                    else if (tempOut.equals("Fahrenheit"))
                        return (value * 9 / 5) + 32;
                } else {
                    Toast.makeText(this, "Please input value", Toast.LENGTH_SHORT).show();
                    return -1000;
                }
                return value;
            }
            case "Kelvin": {
                if (value >= MINIMUM_KELVIN) {
                    if (tempOut.equals("Celsius"))
                        return value - 273.15f;
                    else if (tempOut.equals("Fahrenheit"))
                        return (value - 273.15f) * 9 / 5 + 32;
                } else {
                    Toast.makeText(this, "Please input value", Toast.LENGTH_SHORT).show();
                    return -1000;
                }
                return value;
            }
            case "Fahrenheit": {
                if (value >= MINIMUM_FAHRENHEIT) {
                    if (tempOut.equals("Celsius"))
                        return (value - 32) * 5 / 9;
                    else if (tempOut.equals("Kelvin"))
                        return (value - 32) * 5 / 9 + 273.15f;
                } else {
                    Toast.makeText(this, "Please input value", Toast.LENGTH_SHORT).show();
                    return -1000;
                }
                return value;

            }
            default:
                return value;
        }
    }


    private void initList(){
        {{
            temperatures.put(getString(R.string.temperature_kelvin), 1f);
            temperatures.put(getString(R.string.temperature_celsius), 1f);
            temperatures.put(getString(R.string.temperature_fahrenheit), 1f);
        }}
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        retainFragment.setListener(null);
    }

    @Override
    public void onNewState(ViewState state) {
        calcBtn.setEnabled(state.isButtonEnabled);
        output.setText(state.result);
    }
}