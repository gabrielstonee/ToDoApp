package com.example.todoapp.model;

import java.io.Serializable;

public class Task implements Serializable {
    //serializable let us transfer data
    private Long id;
    private String taskName;

    public Task() {

    }

    public Task(Long id, String taskName) {
        this.id = id;
        this.taskName = taskName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
