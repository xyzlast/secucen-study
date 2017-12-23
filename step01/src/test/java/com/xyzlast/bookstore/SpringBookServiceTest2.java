package com.xyzlast.bookstore;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BookServiceConfiguration.class)
public class SpringBookServiceTest2 {

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

        BookService bookService = context.getBean(BookService.class);
        assertThat(bookService).isNotNull();
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
