package com.spe.breadcrumbs.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter @Setter
public class Role {
    private Long id;

    private String name;

    private List<Expert> experts;
}
