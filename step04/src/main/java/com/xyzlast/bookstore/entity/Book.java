package com.xyzlast.bookstore.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Column(name = "comment", nullable = true, length = 255)
    private String comment;
    @Column(name = "author", nullable = false, length = 50)
    private String author;
    @Column(name = "publishDate", nullable = false)
    private Date publishDate;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    private BookStatus bookStatus;
    @ManyToOne
    @JoinColumn(name = "rentUserId", nullable = true)
    private User rentUser;
    @OneToMany(mappedBy = "book")
    private List<History> histories;
}
