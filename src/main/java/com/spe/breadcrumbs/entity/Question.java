package com.spe.breadcrumbs.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "question")
public class Question {
    @Id @GeneratedValue(strategy = GenerationType.AUTO) @Getter
    private Long id;
    @Column(name = "question") @Getter @Setter
    private String question;
    @Column(name = "no_of_attempts") @Getter @Setter
    private int noOfAttempts;
    @Column(name = "score") @Getter @Setter
    private int score;
    private List<Choices> choices = new ArrayList<>();
    public Question(Long id,String question,int noOfAttempts,int score) {
        this.id = id;
        this.question = question;
        this.noOfAttempts = noOfAttempts;
        this.score = score;
    }
}
