package com.xyzlast.bookstore.service;

import com.xyzlast.bookstore.entity.History;
import com.xyzlast.bookstore.entity.User;

import java.util.List;

public interface UserService {
    boolean rent(int userId, int bookId);
    boolean returnBook(int userId, int bookId);
    List<User> listup();
    List<History> getHistories(int userId);
}
