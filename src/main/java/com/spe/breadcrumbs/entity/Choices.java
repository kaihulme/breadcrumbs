package com.spe.breadcrumbs.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity @Table(name = "Choices")
public class Choices {

   private Question question;
   @Column(name = "choiceText") @Getter @Setter
   private String choiceText;
   @Column(name = "answer") @Getter @Setter
   private boolean answer;

}
