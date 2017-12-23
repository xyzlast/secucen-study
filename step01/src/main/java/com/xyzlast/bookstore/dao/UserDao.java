package com.xyzlast.bookstore.dao;

import com.xyzlast.bookstore.util.ConnectionFactory;
import com.xyzlast.bookstore.entity.User;

import java.util.List;

public class UserDao implements BookStoreDao<User, Integer> {

    private final ConnectionFactory connectionFactory;

    public UserDao(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public int countAll() throws Exception {
        return 0;
    }

    @Override
    public void deleteAll() throws Exception {

    }

    @Override
    public List<User> getAll() throws Exception {
        return null;
    }

    @Override
    public User getById(Integer id) throws Exception {
        return null;
    }

    @Override
    public boolean update(User entity) throws Exception {
        return false;
    }

    @Override
    public boolean add(User entity) throws Exception {
        return false;
    }

    @Override
    public boolean delete(User entity) throws Exception {
        return false;
    }
}
