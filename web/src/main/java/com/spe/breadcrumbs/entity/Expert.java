package com.spe.breadcrumbs.entity;


import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity @Getter
public class Expert {
    @Id
    private Long id;

    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    private List<Quiz> quizzes = new ArrayList<>();

    public Expert(Long id,String firstName,String lastName,String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email; //can use regex to pattern match
        this.password = password; // can also use regex to pattern match
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return firstName + ' ' + lastName + ' '+  email;
    }
}
