package com.xyzlast.bookstore.config;

import com.xyzlast.bookstore.ac.Hello;
import com.xyzlast.bookstore.aop.ServiceAdvisor;
import com.xyzlast.bookstore.aop.ServiceMethodAdvice;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {
    "com.xyzlast.bookstore.dao",
    "com.xyzlast.bookstore.service"
})
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class BookStoreConfig {

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:4306/bookstore");
        dataSource.setUsername("root");
        dataSource.setPassword("qwer12#$");
        dataSource.setMaximumPoolSize(10);
        dataSource.setMinimumIdle(10);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public BeanNameAutoProxyCreator beanNameAutoProxyCreator() {
        BeanNameAutoProxyCreator beanNameAutoProxyCreator = new BeanNameAutoProxyCreator();
        beanNameAutoProxyCreator.setBeanNames("userServiceImpl");
        beanNameAutoProxyCreator.setInterceptorNames("methodInterceptor");
        return beanNameAutoProxyCreator;
    }

    @Bean(value = "methodInterceptor")
    public ServiceMethodAdvice methodInterceptor() {
        return new ServiceMethodAdvice();
    }

    @Bean
    public ServiceAdvisor serviceAdvisor() {
        return new ServiceAdvisor();
    }

    @Bean(value = "hello")
    @Scope(value = BeanDefinition.SCOPE_SINGLETON)
    public Hello hello() {
        return new Hello();
    }

    @Bean(value = "hello1")
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public Hello hello1() {
        return new Hello();
    }
}
