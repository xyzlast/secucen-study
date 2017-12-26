package com.xyzlast.bookstore.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface InnerPreparedStatementProcess {
    void doProcess(PreparedStatement st) throws SQLException;
}
