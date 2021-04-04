package com.example.lab1;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lab1.tasks.ConvertCallable;
import com.example.lab1.tasks.Task;
import com.example.lab1.tasks.TaskListener;
import com.example.lab1.tasks.ThreadTask;

import java.util.HashMap;
import java.util.Map;

public class RetainFragment extends Fragment {

    private static final float MINIMUM_CELSIUS = -273.15f;
    private static final float MINIMUM_KELVIN = 0;
    private static final float MINIMUM_FAHRENHEIT = -459.67f;

    private ViewState viewState = new ViewState();
    private StateListener listener;

    private Task<Float> currentTask;

    public static final String TAG = RetainFragment.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public ViewState getViewState(){
        return viewState;
    }

    public void setListener(StateListener listener){
        this.listener = listener;
        if(listener!=null){
            listener.onNewState(viewState);
        }
    }

    public float convert(float value, float inputMult, float outputMult) {
        currentTask = createConvertTask(value, inputMult, outputMult);
        viewState.isButtonEnabled = true;
        viewState.showResult = false;
        viewState.result = "";
        updateState();
        currentTask.execute(new TaskListener<Float>() {
            @Override
            public void onSuccess(Float result) {
                viewState.isButtonEnabled = true;
                viewState.showResult = true;
                viewState.result = String.valueOf(result);
                updateState();
            }

            @Override
            public void onError(Throwable error) {
                Log.e(TAG, "Error", error);
                viewState.isButtonEnabled = true;
                viewState.showResult = false;
                viewState.result = "";
                updateState();
            }
        });

        return value * inputMult / outputMult;
    }

    public float convertTemp(float value, String tempIn, String tempOut) {
        switch (tempIn) {
            case "Celsius": {
                if (value >= MINIMUM_CELSIUS) {
                    if (tempOut.equals("Kelvin"))
                        return value + 273.15f;
                    else if (tempOut.equals("Fahrenheit"))
                        return (value * 9 / 5) + 32;
                } else {
                    Toast.makeText(getContext(), "Please input value", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getContext(), "Please input value", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getContext(), "Please input value", Toast.LENGTH_SHORT).show();
                    return -1000;
                }
                return value;

            }
            default:
                return value;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(currentTask!=null){
            currentTask.cancel();
        }
    }

    private void updateState(){
        if(listener!=null){
            listener.onNewState(viewState);
        }
    }

    private Task<Float> createConvertTask(float value, float inputMult, float outputMult){
        return new ThreadTask<>(new ConvertCallable(value, inputMult, outputMult));
    }

    public interface StateListener {
        void onNewState(ViewState state);
    }
}
