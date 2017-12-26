package com.xyzlast.bookstore.dao;

import com.xyzlast.bookstore.entity.Book;
import com.xyzlast.bookstore.util.ConnectionFactory;
import com.xyzlast.bookstore.entity.BookStatus;
import com.xyzlast.bookstore.util.InnerPreparedStatementAndResultSetProcess;
import com.xyzlast.bookstore.util.SqlExecutor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao implements BookStoreDao<Book, Integer> {

    private final SqlExecutor sqlExecutor;

    public BookDao(SqlExecutor sqlExecutor) {
        this.sqlExecutor = sqlExecutor;
    }

    @Override
    public int countAll() {
        String sql = "select count(*) from books";
        return sqlExecutor.executeProcess(sql, new InnerPreparedStatementAndResultSetProcess<Integer>() {
            @Override
            public void doProcess(PreparedStatement st) throws SQLException {

            }

            @Override
            public Integer convertFromResultSet(ResultSet rs) throws SQLException{
                rs.next();
                Long count = rs.getLong(1);
                return count.intValue();
            }
        });
    }

    @Override
    public void deleteAll() {
        sqlExecutor.executeProcess("delete from books", (st) -> {
        });
    }

    @Override
    public List<Book> getAll() {
        String sql = "select id, name, author, status, rentUserId, comment, publishDate from books";
        return sqlExecutor.executeProcess(sql, new InnerPreparedStatementAndResultSetProcess<List<Book>>() {
            @Override
            public void doProcess(PreparedStatement st) throws SQLException {

            }

            @Override
            public List<Book> convertFromResultSet(ResultSet rs) throws SQLException {
                List<Book> books = new ArrayList<>();
                while (rs.next()) {
                    books.add(convertBookFromResultSet(rs));
                }
                return books;
            }
        });
    }

    @Override
    public Book getById(Integer id) {
        String sql = "select id, name, author, publishDate, comment, status, rentUserId from books where id=?";
        return sqlExecutor.executeProcess(sql, new InnerPreparedStatementAndResultSetProcess<Book>() {
            @Override
            public void doProcess(PreparedStatement st) throws SQLException {
                st.setInt(1, id);
            }

            @Override
            public Book convertFromResultSet(ResultSet rs) throws SQLException {
                Book book = null;
                if (rs.next()) {
                    book = convertBookFromResultSet(rs);
                }
                return book;
            }
        });
    }

    @Override
    public boolean update(Book book) {
        sqlExecutor.executeProcess("update books set name = ?, author = ?, publishDate = ?, comment = ?, status = ?, rentUserId =? where id = ?", (st) -> {
            st.setString(1, book.getName());
            st.setString(2, book.getAuthor());
            st.setDate(3, new java.sql.Date(book.getPublishDate().getTime()));
            st.setString(4, book.getComment());
            st.setInt(5, book.getBookStatus().value());
            st.setObject(6, book.getRentUserId());
            st.setInt(7, book.getId());
        });
        return true;
    }

    @Override
    public boolean add(Book book) {
        sqlExecutor.executeProcess("insert books(name, author, publishDate, comment, status, rentUserId) values(?, ?, ?, ?, ?, ?)", (st) -> {
            st.setString(1, book.getName());
            st.setString(2, book.getAuthor());
            java.sql.Date sqlDate = new java.sql.Date(book.getPublishDate().getTime());
            st.setDate(3, sqlDate);
            st.setString(4, book.getComment());
            st.setInt(5, book.getBookStatus().value());
            st.setObject(6, book.getRentUserId());
        });
        return true;
    }

    @Override
    public boolean delete(Book entity) {
        sqlExecutor.executeProcess("delete from books where id = ?", (st) -> {
            st.setInt(1, entity.getId());
        });
        return true;
    }

    private Book convertBookFromResultSet(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setName(rs.getString("name"));
        book.setAuthor(rs.getString("author"));
        java.util.Date date = new java.util.Date(rs.getDate("publishDate").getTime());
        book.setPublishDate(date);
        book.setPublishDate(date);
        book.setBookStatus(BookStatus.parse(rs.getInt("status")));
        Integer rentUserId = (Integer) rs.getObject("rentUserId");
        book.setRentUserId(rentUserId);
        book.setComment(rs.getString("comment"));

        return book;
    }
}
