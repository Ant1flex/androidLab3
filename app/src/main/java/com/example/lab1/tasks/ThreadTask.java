package com.example.lab1.tasks;

import java.util.concurrent.Callable;

public class ThreadTask<T> extends ConvertTask<T> {

    private Callable<T> callable;
    private Thread thread;

    public ThreadTask(Callable<T> callable){
        this.callable = callable;
    }

    @Override
    protected void start() {
        thread = new Thread(()->{
            try{
                T result = callable.call();
                publishSuccess(result);
            } catch (Exception e) {
                if(!(e instanceof InterruptedException)){
                    publishError(e);
                }
            }
        });
        thread.start();
    }

    @Override
    protected void onCancelled() {
        thread.interrupt();
    }
}
