package com.spe.breadcrumbs.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "question")
@Getter @Setter
public class Question {

    @Id @GeneratedValue(strategy = GenerationType.AUTO) @Getter
    private Long id;

    @Column(name = "question") @Getter @Setter
    private String question;

    @Column(name = "x_coord")
    private int x_coord;

    @Column(name = "y_coord")
    private int y_coord;

    public Question(Long id, String question, int x_coord, int y_coord) {
        this.id = id;
        this.question = question;
        this.x_coord = x_coord;
        this.y_coord = y_coord;
    }

    public Question() {}

    Long getId (Question q) {
        return id;
    }

    String getQuestion (Question q) {
        return question;
    }

    @Getter @Setter
    private Quiz quiz;

    @Getter
    private int noOfAttempts = 0;

    @Getter
    private int score = 0;

    @Getter @Setter
    private List<Choice> attempts = new ArrayList<>(); //stores the order the user made the attempts (can be less than 4)

    @Getter @Setter
    private List<Choice> choices = new ArrayList<>();

}
