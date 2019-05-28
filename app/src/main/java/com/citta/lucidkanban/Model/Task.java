package com.citta.lucidkanban.Model;

import android.media.Image;

import java.util.Date;

public class Task {
    private int taskId;
    private String taskTitle;
    private String taskDescription;
    private Date taskDate;
    private Image taskImage;

    public Task(int taskId, String taskTitle, String taskDescription, Date taskDate, Image taskImage)
    {
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskDate = taskDate;
        this.taskImage = taskImage;
    }
}
