package com.xyzlast.bookstore.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private int id;
    private String name;
    private String password;
    private int point;
    private int level;
}
