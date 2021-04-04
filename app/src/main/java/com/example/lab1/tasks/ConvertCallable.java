package com.example.lab1.tasks;

import java.util.concurrent.Callable;

public class ConvertCallable implements Callable<Float> {

    private float value;
    private float inputMult;
    private float outputMult;

    public ConvertCallable(float value, float inputMult, float outputMult){
        this.value = value;
        this.inputMult = inputMult;
        this.outputMult = outputMult;
    }

    @Override
    public Float call() throws Exception{
        Thread.sleep(1000);
        return value * inputMult / outputMult;
    }

}
