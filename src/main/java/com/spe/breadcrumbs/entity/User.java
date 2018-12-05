package com.spe.breadcrumbs.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity @Table(name = "User")
public class User {
    @Id @GeneratedValue
    private int id;
    @Column(name = "firstName") @Getter @Setter
    private String firstName;

    @Column(name = "lastName") @Getter @Setter
    private String lastName;

    @Column(name = "email") @Getter @Setter
    private String email;

    @Column(name = "code") @Getter @Setter
    private String code;

    @Column(name = "score") @Getter @Setter
    private int score;
}
