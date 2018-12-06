package com.spe.breadcrumbs.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "User")
@Getter @Setter
public class User {
    @Id @GeneratedValue
    private Long id;
    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "code")
    private String code;

    @Column(name = "score")
    private int score;

    @Getter @Setter
    List<Question> questions = new ArrayList<>();

    public User(Long id,String firstName,String lastName,String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email; //can use regex to pattern match
        this.score= 0;
    }

    public boolean equals(User u) {
        return (id.equals(u.getId()) || (u.getEmail().toLowerCase().equals(email)));
    }

    @Override
    public String toString() {
        return "id =" + id + " "
                + "First name: " + firstName
                + " Last name: " + lastName
                + " email: " + email
                + " score: " + score;
    }
}
