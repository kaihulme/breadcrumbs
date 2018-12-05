package com.spe.breadcrumbs.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity @Table(name = "ActivityManager")
public class ActivityManager {
    @Column(name = "firstName") @Getter @Setter
    private String firstName;

    @Column(name = "lastName") @Getter @Setter
    private String lastName;
    @Column(name = "userame") @Getter @Setter
    private String username;
    @Column(name = "password")  @Setter
    private String password;

    public ActivityManager(String firstName,String lastName,String username,String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

}
