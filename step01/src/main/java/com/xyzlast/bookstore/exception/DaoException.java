package com.xyzlast.bookstore.exception;

import java.sql.SQLException;

public class DaoException extends RuntimeException {
    private static final long serialVersionUID = -370355408003871284L;

    public DaoException(Exception exception) {
        super(exception);
    }

    public DaoException(SQLException sqlException) {
        super(sqlException);
    }

    public DaoException(InstantiationException instantiationException) {
        super(instantiationException);
    }

    public DaoException(IllegalAccessException illegalAccessException) {
        super(illegalAccessException);
    }

    public DaoException(ClassNotFoundException classNotFoundException) {
        super(classNotFoundException);
    }
}
