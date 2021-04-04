package com.example.lab1.tasks;

public interface Task<T> {
    void execute(TaskListener<T> listener);

    void cancel();
}
