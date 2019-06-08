package com.citta.lucidkanban.model;

import android.widget.ImageView;

import java.io.Serializable;

public class Task implements Serializable {
    public int taskId;
    public String taskTitle;
    public String taskDescription;
    public String taskDate;
//    public ImageView taskImage;

    public Task(int taskId, String taskTitle, String taskDescription, String taskDate)
    {
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskDate = taskDate;
//        this.taskImage = taskImage;
    }
}
