package com.xyzlast.bookstore.dao;

import com.xyzlast.bookstore.entity.Book;
import com.xyzlast.bookstore.entity.BookStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class BookDao implements BookStoreDao<Book, Integer> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Integer countAll() {
        String sql = "select count(*) from books";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public void deleteAll() {
        String sql = "delete from books";
        jdbcTemplate.update(sql);
    }

    @Override
    public List<Book> getAll() {
        String sql = "select id, name, author, status, rentUserId, comment, publishDate from books";
        List<Book> books = new ArrayList<>();
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        for (Map<String, Object> map : maps) {
            books.add(convertBookFromResultSet(map));
        }
        return books;
    }

    @Override
    public Book getById(Integer id) {
        String sql = "select id, name, author, publishDate, comment, status, rentUserId from books where id=?";
        return jdbcTemplate.queryForObject(sql, new Object[] {id}, (rs, rowNum) -> convertBookFromResultSet(rs));
    }

    @Override
    public boolean update(Book book) {
        String sql = "update books set name = ?, author = ?, publishDate = ?, comment = ?, status = ?, rentUserId =? " +
            "where id = ?";
        return jdbcTemplate.update(sql,
            book.getName(), book.getAuthor(),
            new java.sql.Date(book.getPublishDate().getTime()),
            book.getComment(),
            book.getBookStatus().value(),
            book.getRentUserId(),
            book.getId()) == 1;
    }

    @Override
    public boolean add(Book book) {
        String sql = "insert books(name, author, publishDate, comment, status, rentUserId) values(?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
            book.getName(), book.getAuthor(),
            new java.sql.Date(book.getPublishDate().getTime()),
            book.getComment(),
            book.getBookStatus().value(),
            book.getRentUserId()) == 1;
    }

    @Override
    public boolean delete(Book entity) {
        String sql = "delete from books where id = ?";
        return jdbcTemplate.update(sql, entity.getId()) == 1;
    }

    private Book convertBookFromResultSet(Map rs) {
        Book book = new Book();
        book.setId((int) rs.get("id"));
        book.setName((String) rs.get("name"));
        book.setAuthor((String) rs.get("author"));
        book.setPublishDate((Timestamp) rs.get("publishDate"));
        book.setBookStatus(BookStatus.parse((int) rs.get("status")));
        Integer rentUserId = (Integer) rs.get("rentUserId");
        book.setRentUserId(rentUserId);
        book.setComment((String) rs.get("comment"));
        return book;
    }

    private Book convertBookFromResultSet(ResultSet rs) throws SQLException {
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
        return book;
    }
}
