package com.xyzlast.bookstore.dao;

import com.xyzlast.bookstore.entity.Book;
import com.xyzlast.bookstore.ConnectionFactory;
import com.xyzlast.bookstore.entity.BookStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao implements BookStoreDao<Book, Integer> {

    private final ConnectionFactory connectionFactory;

    public BookDao(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public int countAll() throws Exception {
        Connection conn = connectionFactory.getConnection();
        PreparedStatement st = conn.prepareStatement("select count(*) from books");
        ResultSet rs = st.executeQuery();
        rs.next();

        Long count = rs.getLong(1);

        rs.close();
        st.close();
        conn.close();

        return count.intValue();
    }

    @Override
    public void deleteAll() throws Exception {
        Connection conn = connectionFactory.getConnection();
        PreparedStatement st = conn.prepareStatement("delete from books");
        st.execute();
        st.close();
        conn.close();
    }

    @Override
    public List<Book> getAll() throws Exception {
        Connection conn = connectionFactory.getConnection();
        PreparedStatement st = conn.prepareStatement("select id, name, author, status, rentUserId, comment, publishDate from books");
        ResultSet rs = st.executeQuery();

        List<Book> books = new ArrayList<>();
        while (rs.next()) {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setName(rs.getString("name"));
            book.setAuthor(rs.getString("author"));
            java.util.Date date = new java.util.Date(rs.getDate("publishDate").getTime());
            book.setPublishDate(date);
            book.setBookStatus(BookStatus.parse(rs.getInt("status")));
            Integer rentUserId = (Integer) rs.getObject("rentUserId");
            book.setRentUserId(rentUserId);
            book.setComment(rs.getString("comment"));
            books.add(book);
        }
        rs.close();
        st.close();
        conn.close();
        return books;
    }

    @Override
    public Book getById(Integer id) throws Exception {
        Connection conn = connectionFactory.getConnection();
        PreparedStatement st = conn.prepareStatement("select id, name, author, publishDate, comment, status, rentUserId from books where id=?");
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();

        Book book = null;
        if (rs.next()) {
            book = new Book();
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
        }
        rs.close();
        st.close();
        conn.close();
        return book;
    }

    @Override
    public boolean update(Book book) throws Exception {
        Connection conn = connectionFactory.getConnection();
        PreparedStatement st = conn.prepareStatement("update books set name = ?, author = ?, publishDate = ?, comment = ?, status = ?, rentUserId =? where id = ?");
        st.setString(1, book.getName());
        st.setString(2, book.getAuthor());
        st.setDate(3, new java.sql.Date(book.getPublishDate().getTime()));
        st.setString(4, book.getComment());
        st.setInt(5, book.getBookStatus().value());
        st.setObject(6, book.getRentUserId());
        st.setInt(7, book.getId());
        st.execute();

        st.close();
        conn.close();

        return true;
    }

    @Override
    public boolean add(Book book) throws Exception {
        Connection conn = connectionFactory.getConnection();
        PreparedStatement st = conn.prepareStatement("insert books(name, author, publishDate, comment, status, rentUserId) values(?, ?, ?, ?, ?, ?)");
        st.setString(1, book.getName());
        st.setString(2, book.getAuthor());
        java.sql.Date sqlDate = new java.sql.Date(book.getPublishDate().getTime());
        st.setDate(3, sqlDate);
        st.setString(4, book.getComment());
        st.setInt(5, book.getBookStatus().value());
        st.setObject(6, book.getRentUserId());
        st.execute();
        st.close();
        conn.close();

        return true;
    }

    @Override
    public boolean delete(Book entity) throws Exception {
        Connection conn = connectionFactory.getConnection();
        PreparedStatement st = conn.prepareStatement("delete from books where id = ?");
        st.setInt(1, entity.getId());
        st.execute();

        st.close();
        conn.close();

        return true;
    }
}
