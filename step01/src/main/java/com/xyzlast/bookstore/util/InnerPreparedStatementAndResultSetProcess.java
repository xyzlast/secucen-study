package com.xyzlast.bookstore.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface InnerPreparedStatementAndResultSetProcess<T> {
    void doProcess(PreparedStatement st) throws SQLException;
    T convertFromResultSet(ResultSet rs) throws SQLException;
}
