package com.spe.breadcrumbs.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
//TODO make Role and Expert a many to many relationship i.e Admin has 2 roles
@Getter @Setter
public class Role {
    private Long id;

    private String name;

    private List<Expert> experts;
}
