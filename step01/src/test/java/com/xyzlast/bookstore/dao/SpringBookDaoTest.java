package com.xyzlast.bookstore.dao;

import com.xyzlast.bookstore.BookServiceConfiguration;
import com.xyzlast.bookstore.ConnectionFactory;
import com.xyzlast.bookstore.dao.BookDao;
import org.junit.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class SpringBookDaoTest {

    @Test
    public void checkBeansWithXml() {
        ApplicationContext context = new GenericXmlApplicationContext("/application-context.xml");
        assertThat(context).isNotNull();

        ConnectionFactory connectionFactory01 = (ConnectionFactory) context.getBean("connectionFactory");
        ConnectionFactory connectionFactory02 = (ConnectionFactory) context.getBean("connectionFactory");
        assertThat(connectionFactory01).isEqualTo(connectionFactory02);

        BookDao bookDao = context.getBean(BookDao.class);
        assertThat(bookDao).isNotNull();
    }

    @Test
    public void checkBeansWithConfiguration() {
        ApplicationContext context = new AnnotationConfigApplicationContext(BookServiceConfiguration.class);
        assertThat(context).isNotNull();

        ConnectionFactory connectionFactory01 = (ConnectionFactory) context.getBean("connectionFactory");
        ConnectionFactory connectionFactory02 = (ConnectionFactory) context.getBean("connectionFactory");
        assertThat(connectionFactory01).isEqualTo(connectionFactory02);

        BookDao bookDao = context.getBean(BookDao.class);
        assertThat(bookDao).isNotNull();
    }
}
