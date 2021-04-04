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

public class WeightActivity extends AppCompatActivity implements RetainFragment.StateListener{

    private RetainFragment retainFragment;

    Map<String, Float> weights = new HashMap<String, Float>();

    Spinner spinnerInput;
    Spinner spinnerOutput;
    EditText input;
    EditText output;
    Button calcBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);

        retainFragment = (RetainFragment) getSupportFragmentManager().findFragmentByTag(RetainFragment.TAG);
        if(retainFragment==null){
            retainFragment = new RetainFragment();
            getSupportFragmentManager().beginTransaction().add(retainFragment, RetainFragment.TAG).commit();
        }

        spinnerInput = findViewById(R.id.weightInputList);
        spinnerOutput = findViewById(R.id.weightOutputList);
        input = findViewById(R.id.weightInput);
        output = findViewById(R.id.weightOutput);
        calcBtn = findViewById(R.id.calcWeightBtn);

        initList();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>(weights.keySet()));

        spinnerInput.setAdapter(adapter);
        spinnerOutput.setAdapter(adapter);

        calcBtn.setOnClickListener(v -> {

            float inputValue = Float.parseFloat(input.getText().toString());
            float res = retainFragment.convert(inputValue, weights.get(spinnerInput.getSelectedItem().toString()), weights.get(spinnerOutput.getSelectedItem().toString()));
            output.setText(Float.toString(res));
        });
    }

    private void initList(){
        {{
            weights.put(getString(R.string.weight_gram), 1f);
            weights.put(getString(R.string.weight_kilogram), 1000f);
            weights.put(getString(R.string.weight_ton), 1000000f);
            weights.put(getString(R.string.weight_carat), 0.2f);
            weights.put(getString(R.string.weight_pound), 453.592f);
            weights.put(getString(R.string.weight_pood), 16380.7f);
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