package com.xyzlast.bookstore.repository;

import com.xyzlast.bookstore.entity.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;

import java.util.List;

public class BookDaoImpl implements BookDao {

    private final SessionFactory sessionFactory;

    public BookDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public int countAll() {
        try (Session session = sessionFactory.openSession()) {
            return ((Long) session.createCriteria(Book.class).setProjection(Projections.rowCount()).uniqueResult()).intValue();
        }
    }

    @Override
    public List<Book> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Book", Book.class).list();
        }
    }

    @Override
    public Book getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Book book = session.byId(Book.class).load(id);
            return book;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public int add(Book book) {
        try (Session session = sessionFactory.openSession()) {
            session.save(book);
            return 1;
        }
    }

    @Override
    public int update(Book book) {
        try (Session session = sessionFactory.openSession()) {
            session.save(book);
            return 1;
        }
    }

    @Override
    public int delete(int bookId) {
        try (Session session = sessionFactory.openSession()) {
            Book deleteBook = new Book();
            deleteBook.setId(bookId);
            session.delete(deleteBook);
            return 1;
        }
    }

    @Override
    public void deleteAll() {

    }
}
