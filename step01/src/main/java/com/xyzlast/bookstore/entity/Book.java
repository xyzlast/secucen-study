package com.xyzlast.bookstore.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Book {
    private int id;
    private String name;
    private String comment;
    private String author;
    private Date publishDate;
    private BookStatus bookStatus;
    private Integer rentUserId;
}
