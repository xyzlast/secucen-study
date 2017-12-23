package com.xyzlast.bookstore;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class SpringBookServiceTest {

    @Test
    public void checkBeansWithXml() {
        ApplicationContext context = new GenericXmlApplicationContext("/application-context.xml");
        assertThat(context).isNotNull();

        ConnectionFactory connectionFactory01 = (ConnectionFactory) context.getBean("connectionFactory");
        ConnectionFactory connectionFactory02 = (ConnectionFactory) context.getBean("connectionFactory");
        assertThat(connectionFactory01).isEqualTo(connectionFactory02);

        BookService bookService = context.getBean(BookService.class);
        assertThat(bookService).isNotNull();
    }

    @Test
    public void checkBeansWithConfiguration() {
        ApplicationContext context = new AnnotationConfigApplicationContext(BookServiceConfiguration.class);
        assertThat(context).isNotNull();

        ConnectionFactory connectionFactory01 = (ConnectionFactory) context.getBean("connectionFactory");
        ConnectionFactory connectionFactory02 = (ConnectionFactory) context.getBean("connectionFactory");
        assertThat(connectionFactory01).isEqualTo(connectionFactory02);

        BookService bookService = context.getBean(BookService.class);
        assertThat(bookService).isNotNull();
    }
}
