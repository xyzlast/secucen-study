package com.xyzlast.bookstore.entity;

import com.xyzlast.bookstore.repository.BookDao;
import com.xyzlast.bookstore.repository.UserDao;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private int id;
    @Column(name = "name", length = 50, nullable = false)
    private String name;
    @Column(name = "password", length = 12, nullable = false)
    private String password;
    @Column(name = "point", nullable = false)
    private int point;
    @Column(name = "level", nullable = false)
    private int level;
    @OneToMany(mappedBy = "user")
    private List<History> histories;

    public void rentBook(Book book, int incrementPoint, BookDao bookDao, UserDao userDao) {
        book.setRentUser(this);
        this.setPoint(this.getPoint() + incrementPoint);
        bookDao.update(book);
        userDao.update(this);
    }
}
