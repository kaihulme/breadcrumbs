package com.spe.breadcrumbs.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity @Table(name = "question")
public class Question {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "question") @Getter @Setter
    private String question;
}
