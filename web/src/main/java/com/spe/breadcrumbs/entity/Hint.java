package com.spe.breadcrumbs.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Blob;

@Entity @Table(name = "Hint")
@Setter @Getter
public class Hint {

    Question question;

    @Id
    @GeneratedValue
    private final Long id;

    @Column(name = "hintText")
    private String hintText;

    @Column(name = "x_coord")
    private int x_coord;

    @Column(name = "y_coord")
    private int y_coord;

    @Column(name = "pictureName")
    private String pictureName;

    @Column (name="picture")
    private Blob picture;

    public Hint(Long id, String hintText, int x_coord, int y_coord, String pictureName, Blob picture){
        this.id = id;
        this.hintText = hintText;
        this.x_coord = x_coord;
        this.y_coord = y_coord;
        this.pictureName = pictureName;
        this.picture = picture;
    }

    public Hint() {id = 0L;}

}
