package com.spe.breadcrumbs.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity @Table(name = "Choice")
public class Choice {
   @Getter @Setter
   private Long choiceId;
   @Getter @Setter
   private Long questionId;
   @Column(name = "choiceText") @Getter @Setter
   private String choiceText;
   @Column(name = "answer") @Getter @Setter
   private boolean answer;

   public Choice(Long questionId,String choiceText,boolean answer){
      this.questionId = questionId;
      this.choiceText = choiceText;
      this.answer = answer;
   }
   public Choice(){}

}
