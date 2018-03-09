package com.xyzlast.bookstore.repository;

import com.xyzlast.bookstore.entity.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class BookDaoImpl implements BookDao {

    private final SessionFactory sessionFactory;

    public BookDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public int countAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Long> query = builder.createQuery(Long.class);
            query.select(builder.count(query.from(Book.class)));
            return session.createQuery(query).getSingleResult().intValue();
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
    public int delete(Book book) {
        try (Session session = sessionFactory.openSession()) {
            session.delete(book);
            return 1;
        }
    }

    @Override
    public void deleteAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaDelete<Book> query = builder.createCriteriaDelete(Book.class);
            query.from(Book.class);
            session.createQuery(query);
        }
    }
}
