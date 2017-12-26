package com.xyzlast.bookstore.util;

import org.junit.Before;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;

public class SqlExecutorTest {

    private SqlExecutor sqlExecutor;

    @Before
    public void setUp() throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory("org.mariadb.jdbc.Driver",
            "jdbc:mysql://127.0.0.1:4306/bookstore", "root", "qwer12#$");
        assertThat(connectionFactory).isNotNull();
        assertThat(connectionFactory.getConnection()).isNotNull();
        sqlExecutor = new SqlExecutor(connectionFactory);
    }

    @Test
    public void executeProcessWithReturn() throws Exception {
        String sql = "SELECT count(*) FROM books";
        InnerPreparedStatementAndResultSetProcess<Integer> process = new InnerPreparedStatementAndResultSetProcess<Integer>() {
            @Override
            public void doProcess(PreparedStatement st) throws SQLException {

            }
            @Override
            public Integer convertFromResultSet(ResultSet rs) throws SQLException {
                rs.next();
                Long countValue = rs.getLong(1);
                return countValue.intValue();
            }
        };
        Integer countResult = sqlExecutor.executeProcess(sql, process);
        assertThat(countResult).isNotNull();
    }

    @Test
    public void executeProcessWithNoReturn() throws Exception {
        String sql = "UPDATE books SET name = ? WHERE id = ?";
        boolean progress = false;
        sqlExecutor.executeProcess(sql, (st) -> {
            st.setString(1, "CH_NAME");
            st.setInt(2, 10);
        });

    }
}
