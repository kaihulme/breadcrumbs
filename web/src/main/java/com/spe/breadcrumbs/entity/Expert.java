package com.spe.breadcrumbs.entity;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
public class Expert {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    private List<Quiz> quizzes = new ArrayList<>();

    public Expert(String firstName,String lastName,String email, String password) {
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
