package com.xyzlast.bookstore.service;

import com.xyzlast.bookstore.entity.Book;
import com.xyzlast.bookstore.entity.User;
import com.xyzlast.bookstore.repository.BookDao;
import com.xyzlast.bookstore.repository.UserDao;

public class UserServiceImpl implements UserService {

    private BookDao bookDao;
    private UserDao userDao;

    @Override
    public void rentBook(int userId, int bookId) {
        Book book = bookDao.getById(bookId);
        User user = userDao.getById(userId);
        user.rentBook(book, 10, bookDao, userDao);
    }
}
