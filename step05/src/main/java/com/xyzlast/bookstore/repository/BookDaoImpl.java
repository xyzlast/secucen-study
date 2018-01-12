package com.xyzlast.bookstore.repository;

import com.querydsl.core.dml.InsertClause;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.xyzlast.bookstore.entity.Book;
import com.xyzlast.bookstore.entity.QBook;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class BookDaoImpl implements BookDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public int countAll() {
        JPAQuery<Book> jpaQuery = new JPAQuery<>(em);
        Long count = jpaQuery.from(QBook.book).fetchCount();
        return count.intValue();
    }

    @Override
    public List<Book> getAll() {
        JPAQuery<Book> jpaQuery = new JPAQuery<>(em);
        return jpaQuery.from(QBook.book).fetch();
    }

    @Override
    public Book getById(int id) {
        JPQLQuery<Book> jpaQuery = new JPAQuery<>(em);
        QBook q = QBook.book;
        return jpaQuery.from(q).where(q.id.eq(id)).fetchFirst();
    }

    @Override
    public int add(Book book) {
        em.merge(book);
        return 1;
    }

    @Override
    public int update(Book book) {
        QBook q = QBook.book;
        JPAUpdateClause updateClause = new JPAUpdateClause(em, q);
        updateClause.where(q.id.eq(book.getId()))
            .set(q.name, book.getName())
            .set(q.author, book.getAuthor())
            .set(q.comment, book.getComment())
            .set(q.publishDate, book.getPublishDate())
            .execute();
        return 1;
    }

    @Override
    public int delete(Book book) {
        QBook q = QBook.book;
        JPADeleteClause jpaDeleteClause = new JPADeleteClause(em, q);
        jpaDeleteClause.where(q.id.eq(book.getId())).execute();
        return 0;
    }

    @Override
    public void deleteAll() {
        JPADeleteClause jpaDeleteClause = new JPADeleteClause(em, QBook.book);
        jpaDeleteClause.execute();
    }
}
