package com.xyzlast.bookstore.util;

import com.xyzlast.bookstore.util.ConnectionFactory;
import org.junit.Test;

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
