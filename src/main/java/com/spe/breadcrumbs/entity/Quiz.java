package com.spe.breadcrumbs.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity @Table(name = "Quiz")
public class Quiz {
    @Id @GeneratedValue
    private int id;
    @Column(name = "title") @Getter @Setter
    private String title;
    @Getter @Setter
    List<Question> questions = new ArrayList<>();

    @Getter
    List<User> users = new ArrayList<>();

    public Quiz(String title){
        this.title = title;
    }

    //returns a question in the quiz based on its id
    public Question findQuestion(Long id){
        for(Question q: questions){
            if(q.getId().equals(id)) return q;
        }
        return null;
    }

}
