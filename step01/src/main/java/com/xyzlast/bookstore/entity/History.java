package com.xyzlast.bookstore.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class History {
    private int id;
    private int bookId;
    private int userId;
    private HistoryActionType actionType;
    private Date date;
}
