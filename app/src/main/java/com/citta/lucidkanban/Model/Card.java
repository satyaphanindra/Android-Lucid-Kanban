package com.citta.lucidkanban.Model;

import java.util.List;

public class Card {

 private List<Member> memberInfo;
 private List<Task> taskInfo;
 private Boolean isCardAssigned;


 public Card(List<Member> memberInfo, List<Task> taskInfo, Boolean isCardAssigned)
 {
     this.memberInfo = memberInfo;
     this.taskInfo = taskInfo;
     this.isCardAssigned = isCardAssigned;

 }

 public static class forEnum
 {

     private enum cardPriority  {LOW, MEDIUM, HIGH;}
     private enum cardStatus { Todo, InProgress, Done}

     private cardPriority severityLevel;
     private cardStatus progressStage;

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
}
