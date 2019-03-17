package com.spe.breadcrumbs.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity @Table(name = "Hint")
public class Hint {

    Question question;

    @Column(name = "hintText") @Setter @Getter
    private String hintText;

    @Column(name = "x_coord")
    private int x_coord;

    @Column(name = "y_coord")
    private int y_coord;

}
