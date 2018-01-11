package com.xyzlast.bookstore.repository;

import com.xyzlast.bookstore.entity.Book;

import java.util.List;

public interface BookDao {
    int countAll();
    List<Book> getAll();
    Book getById(int id);
    int add(Book book);
    int update(Book book);
    int delete(int bookId);
    void deleteAll();
}
