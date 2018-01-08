package com.xyzlast.bookstore.repository;

import com.xyzlast.bookstore.entity.Book;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDaoImpl extends SqlSessionDaoSupport implements BookDao {

    @Autowired
    public BookDaoImpl(SqlSessionTemplate sqlSessionTemplate) {
        super.setSqlSessionTemplate(sqlSessionTemplate);
    }

    @Override
    public int countAll() {
        return getSqlSession().getMapper(BookDao.class).countAll();
    }

    @Override
    public List<Book> getAll() {
        return getSqlSession().getMapper(BookDao.class).getAll();
    }

    @Override
    public Book getById(int id) {
        return getSqlSession().selectOne("com.xyzlast.bookstore.repository.BookDao.getById", id);
    }

    @Override
    public int add(Book book) {
        return getSqlSession().insert("com.xyzlast.bookstore.repository.BookDao.add", book);
    }

    @Override
    public int update(Book book) {
        return getSqlSession().update("com.xyzlast.bookstore.repository.BookDao.update", book);
    }

    @Override
    public int delete(int bookId) {
        return getSqlSession().delete("com.xyzlast.bookstore.repository.BookDao.delete", bookId);
    }
}
