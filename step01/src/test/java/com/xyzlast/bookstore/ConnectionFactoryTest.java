package com.xyzlast.bookstore;

import org.junit.Test;

import org.junit.*;
import org.junit.runner.RunWith;
import static org.assertj.core.api.Assertions.*;

public class ConnectionFactoryTest {

    @Test
    public void doConnectionFactoryTest() throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory("org.mariadb.jdbc.Driver",
            "jdbc:mysql://127.0.0.1:4306/bookstore", "root", "qwer12#$");
        assertThat(connectionFactory).isNotNull();
        assertThat(connectionFactory.getConnection()).isNotNull();
    }

}
