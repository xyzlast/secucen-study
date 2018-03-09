package com.xyzlast.bookstore.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyzlast.bookstore.entity.Book;
import com.xyzlast.bookstore.entity.History;
import com.xyzlast.bookstore.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.Before;
import org.junit.Test;

import org.junit.*;
import org.junit.runner.RunWith;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SuppressWarnings("unused")
public class BookDaoImplTest {
    private ServiceRegistry serviceRegistry;
    private SessionFactory sessionFactory;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(Book.class);
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(History.class);
        serviceRegistry = new StandardServiceRegistryBuilder()
            .applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    @Test
    public void getAll() {
        BookDao bookDao = new BookDaoImpl(sessionFactory);
        bookDao.getAll();
    }

    @Test
    public void countAll() {
        BookDao bookDao = new BookDaoImpl(sessionFactory);
        int count = bookDao.countAll();
        assertThat(count).isGreaterThanOrEqualTo(0);
    }

    @Test
    public void getById() throws Exception {
        BookDao bookDao = new BookDaoImpl(sessionFactory);
        List<Book> books = bookDao.getAll();
        Book preBook = books.get(0);
        Book afterBook = bookDao.getById(preBook.getId());
        User rentUser = afterBook.getRentUser();
        System.out.println("BookName : " + afterBook.getName());
        System.out.println("UserName : " + rentUser.getName());
    }

    @Test
    public void deleteAll() throws Exception {
        BookDao bookDao = new BookDaoImpl(sessionFactory);
        bookDao.deleteAll();
        int count = bookDao.countAll();
        assertThat(count).isEqualTo(0);
    }
}
