package com.xyzlast.web.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Book {
    private String name;
    private String author;
    private String status;

    public Book() {

    }
}
