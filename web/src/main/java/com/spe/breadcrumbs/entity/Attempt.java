package com.spe.breadcrumbs.entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Attempt {
    private User user;
    private Choice choice;
    public Attempt(User u, Choice c){
        this.user = u;
        this.choice = c;
    }

}
