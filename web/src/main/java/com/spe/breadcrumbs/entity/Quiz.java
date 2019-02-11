package com.spe.breadcrumbs.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "Quiz")
public class Quiz {
    @Id @GeneratedValue @Getter
    private int id;
    @Column(name = "title") @Getter @Setter
    private String title;
    @Getter @Setter
    List<Question> questions = new ArrayList<>();

    @Getter @Setter
    List<User> users = new ArrayList<>();

    public Quiz(int id,String title){
        this.id = id;
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
