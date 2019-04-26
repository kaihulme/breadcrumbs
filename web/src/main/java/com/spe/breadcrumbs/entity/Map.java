package com.spe.breadcrumbs.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import java.sql.Blob;

@Entity @Table(name = "Map")
@Getter @Setter
public class Map {

    @Id @GeneratedValue
    private Long id;

    @Column(name="questionId")
    private Long questionId;

    @Column(name="name")
    private String name;

    @Column (name="picture")
    private Blob picture;

    public Map() {}

    public Map(Long questionId, String name, Blob picture) {
        this.questionId = questionId;
        this.name = name;
        this.picture = picture;
    }

}
