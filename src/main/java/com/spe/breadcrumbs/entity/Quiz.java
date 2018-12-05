package com.spe.breadcrumbs.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "Quiz")
public class Quiz {
    @Id @GeneratedValue
    private int id;
    @Column(name = "title") @Getter @Setter
    private String title;
    List<Question> questions = new ArrayList<>();

}
