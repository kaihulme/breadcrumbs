package com.spe.breadcrumbs.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity @Table(name = "Choice")
public class Choice {

   @Getter @Setter
   private Question question;
   @Column(name = "choiceText") @Getter @Setter
   private String choiceText;
   @Column(name = "answer") @Getter @Setter
   private boolean answer;

   public Choice(Question question,String choiceText,boolean answer){
      this.question = question;
      this.choiceText = choiceText;
      this.answer = answer;
   }


}
