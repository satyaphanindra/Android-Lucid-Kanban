package com.citta.lucidkanban.model;

import android.widget.ImageView;

import com.citta.lucidkanban.model.Card.CardStatus;
import com.citta.lucidkanban.model.Card.CardPriority;

import java.io.Serializable;

public class Task implements Serializable {
    public String taskId;
    public String taskTitle;
    public String taskDescription;
    public StringBuilder taskDate;
    public String taskTime;
    public CardPriority cardPriority = CardPriority.LOW;
    public CardStatus cardStatus = CardStatus.TODO;
    public ImageView taskImage;

    public Task(String taskId, String taskTitle, String taskDescription, StringBuilder taskDate, String taskTime)
    {
        this.taskImage = taskImage;
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskDate = taskDate;
        this.taskTime = taskTime;
    }

    public Task(String taskId, String taskTitle, String taskDescription, StringBuilder taskDate, String taskTime,
                CardPriority cardPriority, CardStatus cardStatus)
    {
        this.taskImage = taskImage;
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskDate = taskDate;
        this.taskTime = taskTime;
        this.cardPriority = cardPriority;
        this.cardStatus = cardStatus;
//        this.taskImage = taskImage;
    }

}
