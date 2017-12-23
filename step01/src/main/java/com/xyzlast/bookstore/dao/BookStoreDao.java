package com.xyzlast.bookstore.dao;

import java.util.List;

public interface BookStoreDao<T, K> {
    int countAll() throws Exception;
    void deleteAll() throws Exception;

    List<T> getAll() throws Exception;
    T getById(K id) throws Exception;
    boolean update(T entity) throws Exception;
    boolean add(T entity) throws Exception;
    boolean delete(T entity) throws Exception;
}
