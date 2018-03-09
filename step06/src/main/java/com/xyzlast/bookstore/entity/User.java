package com.xyzlast.bookstore.entity;

import com.xyzlast.bookstore.constant.UserLevel;
import com.xyzlast.bookstore.repository.BookRepository;
import com.xyzlast.bookstore.repository.UserRepository;
import com.xyzlast.bookstore.service.UserLevelDeterminant;
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
    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "level", nullable = false)
    private UserLevel level;
    @OneToMany(mappedBy = "user")
    private List<History> histories;

    public boolean rentBook(Book book, int incrementPoint, UserLevelDeterminant userLevelDeterminant,
                            UserRepository userRepository, BookRepository bookRepository) {
        if (book.getRentUser() != null) {
            throw new RuntimeException("기존 대여 사용자가 있습니다.");
        }
        book.setRentUser(this);
        this.setPoint(this.getPoint() + incrementPoint);
        this.setLevel(userLevelDeterminant.determine(this.getPoint()));
        bookRepository.save(book);
        userRepository.save(this);
        return true;
    }
}
