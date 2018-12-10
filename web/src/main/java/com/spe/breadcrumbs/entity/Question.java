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
    @Getter @Setter
    private List<Choice> attempts = new ArrayList<>(); //stores the order the user made the hints (can be less than 4)
    private List<Choice> choices = new ArrayList<>();
    public Question(Long id,String question,int score) {
        this.id = id;
        this.question = question;
        this.score = score;
    }
}
