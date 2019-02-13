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

}
