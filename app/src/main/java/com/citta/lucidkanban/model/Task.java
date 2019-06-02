package com.citta.lucidkanban.model;

public class Task {
    public String taskId;
    public String taskTitle;
    public String taskDescription;
    public String taskDate;
    public int taskImage;

    public Task(String taskId, String taskTitle, String taskDescription, String taskDate, int taskImage)
    {
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskDate = taskDate;
        this.taskImage = taskImage;
    }
}
