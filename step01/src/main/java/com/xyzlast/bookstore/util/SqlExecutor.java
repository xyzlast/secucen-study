package com.xyzlast.bookstore.util;

import com.xyzlast.bookstore.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlExecutor {

    private final ConnectionFactory connectionFactory;

    public SqlExecutor(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T executeProcess(String sql, InnerPreparedStatementAndResultSetProcess<T> process) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = connectionFactory.getConnection();
            st = conn.prepareStatement(sql);
            process.doProcess(st);
            rs = st.executeQuery();
            return process.convertFromResultSet(rs);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeSqlObjects(conn, st, rs);
        }
    }

    public void executeProcess(String sql, InnerPreparedStatementProcess process) {
        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = connectionFactory.getConnection();
            st = conn.prepareStatement(sql);
            process.doProcess(st);
            st.executeQuery();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        } finally {
            closeSqlObjects(conn, st, null);
        }

    }

    private void closeSqlObjects(Connection conn, PreparedStatement st, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ignored) {
            }
        }
        if (st != null) {
            try {
                st.close();
            } catch (SQLException ignored) {
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ignored) {
            }
        }
    }
}
