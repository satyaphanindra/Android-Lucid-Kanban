package com.citta.lucidkanban.model;

import java.io.Serializable;

public class Card implements Serializable {

 public Member member;
 public Task task;
 public Boolean isCardAssigned;
 public CardPriority cardPriority;
 public CardStatus cardStatus;


 public Card(Member member, Task task, CardPriority cardPriority,
             CardStatus cardStatus, Boolean isCardAssigned)
 {
     this.member = member;
     this.task = task;
     this.cardPriority = cardPriority;
     this.cardStatus = cardStatus;
     this.isCardAssigned = isCardAssigned;

 }

    public enum CardPriority {LOW, MEDIUM, HIGH;}
    public enum CardStatus {TODO, INPROGRESS, COMPLETED}

}

/*

 public static class forEnum
 {

     public enum CardPriority  {LOW, MEDIUM, HIGH;}
     public enum CardStatus { Todo, InProgress, Done}

     public CardPriority severityLevel;
     public CardStatus progressStage;

     public void onSelectPriority(){
         switch (severityLevel){
             case LOW:
                 break;
             case MEDIUM:
                 break;
             case HIGH:
                 break;
         }
     }

     public void onSelectCardStatus(){
         switch (progressStage){
             case Todo:
                 break;
             case InProgress:
                 break;
             case Done:
                 break;
         }
     }
 }
  */