package com.xyzlast.bookstore;

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

    public Connection getConnection() throws InstantiationException, IllegalAccessException,
        ClassNotFoundException, SQLException {
        Class.forName(this.driverName).newInstance();
        return DriverManager.getConnection(this.connectionString, this.username, this.password);
    }
}
