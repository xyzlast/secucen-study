package com.xyzlast.bookstore.util;

import java.sql.PreparedStatement;

public interface InnerPreparedStatementProcess {
    void doProcess(PreparedStatement st) throws Exception;
}
