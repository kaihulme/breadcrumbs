package com.spe.breadcrumbs.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "Quiz")
public class Quiz {
    @Id @GeneratedValue @Getter @Setter
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
    //adds question if it's not in list already
    public void addQuestion(Question question){
        for(Question q: questions){
            if(q.getId().equals(question.getId())) return;
        }
        questions.add(question);
    }

    public void addUser(User user){
        for(User u: users){
            if(u.equals(user)) return;
        }
        users.add(user);
    }

    //returns a question in the quiz based on its id
    public Question findQuestion(Long id){
        for(Question q: questions){
            if(q.getId().equals(id)) return q;
        }
        return null;
    }
}
