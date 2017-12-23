package com.xyzlast.bookstore.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface InnerPreparedStatementAndResultSetProcess<T> {
    void doProcess(PreparedStatement st) throws Exception;
    T convertFromResultSet(ResultSet rs) throws Exception;
}
