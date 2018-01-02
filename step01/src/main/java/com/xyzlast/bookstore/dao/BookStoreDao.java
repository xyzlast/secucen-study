package com.xyzlast.bookstore.dao;

import java.util.List;

public interface BookStoreDao<T, K> {
    int countAll();
    void deleteAll();

    List<T> getAll();
    T getById(K id);
    boolean update(T entity);
    boolean add(T entity);
    boolean delete(T entity);
}
