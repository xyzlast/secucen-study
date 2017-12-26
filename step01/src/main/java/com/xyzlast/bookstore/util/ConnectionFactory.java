package com.xyzlast.bookstore.util;

import com.xyzlast.bookstore.exception.DaoException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@AllArgsConstructor
public class ConnectionFactory {
    @Getter
    private final String driverName;
    @Getter
    private final String connectionString;
    @Getter
    private final String username;
    @Getter
    private final String password;

    public Connection getConnection()  {
        try {
            Class.forName(this.driverName).newInstance();
            return DriverManager.getConnection(this.connectionString, this.username, this.password);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
            throw new DaoException(ex);
        }
    }
}
