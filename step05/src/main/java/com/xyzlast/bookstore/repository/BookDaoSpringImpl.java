package com.xyzlast.bookstore.repository;

import com.xyzlast.bookstore.entity.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public class BookDaoSpringImpl implements BookDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public BookDaoSpringImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public int countAll() {
        Session session = sessionFactory.getCurrentSession();
        return ((Long) session.createCriteria(Book.class).setProjection(Projections.rowCount()).uniqueResult()).intValue();
    }

    @Override
    public List<Book> getAll() {
        Session session = sessionFactory.getCurrentSession();
//        return session.createQuery("from Book", Book.class).list();
        return null;
    }

    @Override
    public Book getById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.byId(Book.class).load(id);
    }

    @Override
    public int add(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.save(book);
        return 1;
    }

    @Override
    public int update(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.update(book);
        return 1;
    }

    @Override
    public int delete(Book book) {
        return 0;
    }

//    @Override
//    public int delete(int bookId) {
//        Session session = sessionFactory.getCurrentSession();
//        Book book = new Book();
//        book.setId(bookId);
//        session.delete(book);
//        return 1;
//    }

    @Override
    public void deleteAll() {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("DELETE FROM Book").executeUpdate();
    }
}
