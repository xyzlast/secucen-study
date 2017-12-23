package com.xyzlast.bookstore.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SqlExecutor {

    private final ConnectionFactory connectionFactory;

    public SqlExecutor(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T executeProcess(String sql, InnerPreparedStatementAndResultSetProcess<T> process) throws Exception {
        Connection conn = connectionFactory.getConnection();
        PreparedStatement st = conn.prepareStatement(sql);
        process.doProcess(st);
        ResultSet rs = st.executeQuery();
        T result = process.convertFromResultSet(rs);
        rs.close();
        st.close();
        conn.close();

        return result;
    }

    public void executeProcess(String sql, InnerPreparedStatementProcess process) throws Exception {
        Connection conn = connectionFactory.getConnection();
        PreparedStatement st = conn.prepareStatement(sql);
        process.doProcess(st);
        st.execute();
        st.close();
        conn.close();
    }
}
