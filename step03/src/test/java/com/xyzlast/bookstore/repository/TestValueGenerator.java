package com.xyzlast.bookstore.repository;

import com.xyzlast.bookstore.entity.Book;
import com.xyzlast.bookstore.entity.BookStatus;

import java.util.Date;

public class TestValueGenerator {

    public static Book generateNewBook() {
        Book newBook = new Book();
        newBook.setName("newName01");
        newBook.setAuthor("newAuthor01");
        newBook.setComment("newComment01");
        newBook.setPublishDate(new Date());
        newBook.setBookStatus(BookStatus.CanRent);
        newBook.setRentUserId(null);
        return newBook;
    }

//    public static User generateNewUser() {
//        User user = new User();
//        user.setName("newUserName01");
//        user.setPassword("newUserrd");
//        user.setPoint(100);
//        user.setLevel(1);
//        return user;
//    }
//
//    public static History generateNewHistory(BookDao bookDao, UserDao userDao) {
//        Book book = bookDao.getAll().get(0);
//        User user = userDao.getAll().get(0);
//
//        History history = new History();
//        history.setBookId(book.getId());
//        history.setUserId(user.getId());
//        history.setActionType(HistoryActionType.RENT_BOOK);
//        history.setDate(new Date());
//
//        return history;
//    }
}
