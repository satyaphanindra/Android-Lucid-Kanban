package com.citta.lucidkanban.model;

public class Card {

 private Member member;
 private Task task;
 private Boolean isCardAssigned;
 private CardPriority cardPriority;
 private CardStatus cardStatus;


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

     private enum CardPriority  {LOW, MEDIUM, HIGH;}
     private enum CardStatus { Todo, InProgress, Done}

     private CardPriority severityLevel;
     private CardStatus progressStage;

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