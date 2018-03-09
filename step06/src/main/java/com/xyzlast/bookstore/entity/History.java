package com.xyzlast.bookstore.entity;

import com.xyzlast.bookstore.repository.HistoryRepository;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "histories")
public class History {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookId")
    private Book book;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "actionType")
    private HistoryActionType actionType;
    @Column(name = "insertDate")
    private Date date;

    public History() {

    }

    public History(User user, Book book, HistoryActionType actionType) {
        this.user = user;
        this.book = book;
        this.actionType = actionType;
    }

    @PrePersist
    public void onPrePersist() {
        date = new Date();
    }

    public void save(HistoryRepository historyRepository) {
        historyRepository.save(this);
    }
}
