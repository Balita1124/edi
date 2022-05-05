package com.balita.edi.tasks.tasks;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException() {
    }

    public TaskNotFoundException(Long taskId) {
        super("Task: " + taskId + " not found.");
    }
}
