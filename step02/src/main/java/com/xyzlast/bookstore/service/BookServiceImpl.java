package com.xyzlast.bookstore.service;

import com.xyzlast.bookstore.dao.BookDao;
import com.xyzlast.bookstore.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private BookDao bookDao;

    @Autowired
    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public List<Book> listup() {
        return bookDao.getAll();
    }
}
