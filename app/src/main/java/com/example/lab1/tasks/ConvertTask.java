package com.example.lab1.tasks;

import android.os.Looper;

import java.util.logging.Handler;

public abstract class ConvertTask<T> implements Task<T> {

    //private static final Handler HANDLER = new Handler(Looper.getMainLooper());
    private TaskListener<T> taskListener;
    private boolean executed;
    private boolean cancelled;

    @Override
    public void execute(TaskListener<T> listener) {
        if(executed) throw new RuntimeException("Task has been already executed");
        if(cancelled) return;
        executed = true;
        this.taskListener = listener;
        start();
    }

    @Override
    public void cancel() {
        if(!cancelled){
            cancelled=true;
            taskListener = null;
            onCancelled();
        }
    }

    protected final void publishSuccess(T result){
        runOnMainThread(()->{
            if(taskListener!=null){
                taskListener.onSuccess(result);
                taskListener = null;
            }
        });
    }

    protected final void publishError(Throwable error){
        runOnMainThread(()->{
            if(taskListener!=null){
                taskListener.onError(error);
                taskListener = null;
            }
        });
    }

    private void runOnMainThread(Runnable action){
        if(Thread.currentThread().getId() == Looper.getMainLooper().getThread().getId()){
            action.run();
        } else {
            //HANDLER.post(action);
        }
    }

    protected abstract void start();

    protected abstract void onCancelled();
}
