package com.xyzlast.bookstore.repository;

import com.xyzlast.bookstore.entity.User;

import java.util.List;

public interface UserDao {
    int countAll();
    List<User> getAll();
    User getById(int id);
    int add(User user);
    int update(User user);
    int delete(User user);
    void deleteAll();
}
