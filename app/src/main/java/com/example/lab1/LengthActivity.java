package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lab1.tasks.ConvertCallable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LengthActivity extends AppCompatActivity implements RetainFragment.StateListener{

    private RetainFragment retainFragment;

    Map<String, Float> lengths = new HashMap<String, Float>();

    Spinner spinnerInput;
    Spinner spinnerOutput;
    EditText input;
    EditText output;
    Button calcBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_length);

        retainFragment = (RetainFragment) getSupportFragmentManager().findFragmentByTag(RetainFragment.TAG);
        if(retainFragment==null){
            retainFragment = new RetainFragment();
            getSupportFragmentManager().beginTransaction().add(retainFragment, RetainFragment.TAG).commit();
        }

        spinnerInput = findViewById(R.id.lengthInputList);
        spinnerOutput = findViewById(R.id.lengthOutputList);
        input = findViewById(R.id.lengthInput);
        output = findViewById(R.id.lengthOutput);
        calcBtn = findViewById(R.id.calcLengthBtn);

        initList();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>(lengths.keySet()));

        spinnerInput.setAdapter(adapter);
        spinnerOutput.setAdapter(adapter);


        calcBtn.setOnClickListener(v -> {

            float inputValue = Float.parseFloat(input.getText().toString());
            float res = retainFragment.convert(inputValue, lengths.get(spinnerInput.getSelectedItem().toString()), lengths.get(spinnerOutput.getSelectedItem().toString()));
            output.setText(Float.toString(res));
        });

        retainFragment.setListener(this);
    }

    private void initList(){
        {{
            lengths.put(getString(R.string.length_centimeter), 1f);
            lengths.put(getString(R.string.length_meter), 100f);
            lengths.put(getString(R.string.length_kilometer), 100000f);
            lengths.put(getString(R.string.length_inch), 2.54f);
            lengths.put(getString(R.string.length_mile), 16.0934f);
            lengths.put(getString(R.string.length_yard), 91.44f);
            lengths.put(getString(R.string.length_foot), 30.48f);
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