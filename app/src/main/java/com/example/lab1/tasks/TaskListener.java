package com.example.lab1.tasks;

public interface TaskListener<T> {
    void onSuccess(T result);
    void onError(Throwable error);
}
