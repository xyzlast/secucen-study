package com.xyzlast.bookstore.dao;

import com.xyzlast.bookstore.BookServiceConfiguration;
import com.xyzlast.bookstore.ConnectionFactory;
import com.xyzlast.bookstore.dao.BookDao;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.*;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BookServiceConfiguration.class)
public class SpringBookDaoTest2 {

    @Autowired
    private ApplicationContext context;

    @Before
    public void setUp() {
        assertThat(context).isNotNull();
    }

    @Test
    public void checkBeansWithConfiguration() {
        ConnectionFactory connectionFactory01 = (ConnectionFactory) context.getBean("connectionFactory");
        ConnectionFactory connectionFactory02 = (ConnectionFactory) context.getBean("connectionFactory");
        assertThat(connectionFactory01).isEqualTo(connectionFactory02);

        BookDao bookDao = context.getBean(BookDao.class);
        assertThat(bookDao).isNotNull();
    }

    @Test
    public void checkBeanLifeCycle() {
        ConnectionFactory singleton01 = (ConnectionFactory) context.getBean("connectionFactoryWithSingleton");
        ConnectionFactory singleton02 = (ConnectionFactory) context.getBean("connectionFactoryWithSingleton");
        assertThat(singleton01.hashCode()).isEqualTo(singleton02.hashCode());

        ConnectionFactory proxy01 = (ConnectionFactory) context.getBean("connectionFactoryWithProxy");
        ConnectionFactory proxy02 = (ConnectionFactory) context.getBean("connectionFactoryWithProxy");
        assertThat(proxy01.hashCode()).isNotEqualTo(proxy02.hashCode());
    }
}
