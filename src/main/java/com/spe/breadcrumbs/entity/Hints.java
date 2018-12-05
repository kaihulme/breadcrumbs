package com.spe.breadcrumbs.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity @Table(name = "Hints")
public class Hints {
    Question question;
    @Column(name = "hintText") @Setter @Getter
    private String hintText;

}
