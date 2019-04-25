package com.spe.breadcrumbs.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Blob;

@Entity @Table(name = "Hint")
@Setter @Getter
public class Hint {

    Question question;

    @Column(name = "hintText")
    private String hintText;

    @Column(name = "x_coord")
    private int x_coord;

    @Column(name = "y_coord")
    private int y_coord;

    @Column (name="picture")
    private Blob picture;

    public Hint(String hintText,int x_coord,int y_coord, Blob picture){
        this.hintText = hintText;
        this.x_coord =x_coord;
        this.y_coord = y_coord;
        this.picture = picture;
    }

}
