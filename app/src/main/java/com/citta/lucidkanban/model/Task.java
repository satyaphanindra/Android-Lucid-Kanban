package com.citta.lucidkanban.model;

import com.citta.lucidkanban.model.Card.CardStatus;
import com.citta.lucidkanban.model.Card.CardPriority;

import java.io.Serializable;

public class Task implements Serializable {
    public int taskId;
    public String taskTitle;
    public String taskDescription;
    public String taskDate;
    public CardPriority cardPriority = CardPriority.LOW;
    public CardStatus cardStatus = CardStatus.TODO;
//    public ImageView taskImage;

    public Task(int taskId, String taskTitle, String taskDescription, String taskDate)
    {
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskDate = taskDate;
//        this.taskImage = taskImage;
    }

    public Task(int taskId, String taskTitle, String taskDescription, String taskDate,
                CardPriority cardPriority, CardStatus cardStatus)
    {
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskDate = taskDate;
        this.cardPriority = cardPriority;
        this.cardStatus = cardStatus;
//        this.taskImage = taskImage;
    }

}
