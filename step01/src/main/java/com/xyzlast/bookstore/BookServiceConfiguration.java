package com.xyzlast.bookstore;

import com.xyzlast.bookstore.dao.BookDao;
import com.xyzlast.bookstore.util.ConnectionFactory;
import com.xyzlast.bookstore.util.SqlExecutor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class BookServiceConfiguration {

    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactory connectionFactory = new ConnectionFactory("org.mariadb.jdbc.Driver",
            "jdbc:mysql://127.0.0.1:4306/bookstore", "root", "qwer12#$");
        return connectionFactory;
    }

    @Bean
    public SqlExecutor sqlExecutor() {
        return new SqlExecutor(connectionFactory());
    }

    @Bean
    public BookDao bookService() {
        return new BookDao(sqlExecutor());
    }

    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON)
    public ConnectionFactory connectionFactoryWithSingleton() {
        ConnectionFactory connectionFactory = new ConnectionFactory("org.mariadb.jdbc.Driver",
            "jdbc:mysql://127.0.0.1:4306/bookstore", "root", "qwer12#$");
        return connectionFactory;
    }

    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public ConnectionFactory connectionFactoryWithProxy() {
        ConnectionFactory connectionFactory = new ConnectionFactory("org.mariadb.jdbc.Driver",
            "jdbc:mysql://127.0.0.1:4306/bookstore", "root", "qwer12#$");
        return connectionFactory;
    }
}
