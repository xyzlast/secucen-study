package com.xyzlast.bookstore.dao;

import com.xyzlast.bookstore.entity.Book;
import com.xyzlast.bookstore.ConnectionFactory;
import com.xyzlast.bookstore.entity.BookStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BookDaoTest {

    private BookDao bookDao;

    @Before
    public void setUp() {
        ConnectionFactory connectionFactory = new ConnectionFactory("org.mariadb.jdbc.Driver",
            "jdbc:mysql://127.0.0.1:4306/bookstore", "root", "qwer12#$");
        bookDao = new BookDao(connectionFactory);
    }

    private Book generateNewBook() {
        Book newBook = new Book();
        newBook.setName("newName01");
        newBook.setAuthor("newAuthor01");
        newBook.setComment("newComment01");
        newBook.setPublishDate(new Date());
        newBook.setBookStatus(BookStatus.CanRent);
        newBook.setRentUserId(null);
        return newBook;
    }

    @Test
    public void getTest() throws Exception {
        List<Book> allBooks = bookDao.getAll();
        if (allBooks.isEmpty()) {
            bookDao.add(generateNewBook());
            allBooks = bookDao.getAll();
        }
        Book checkBook = allBooks.get(allBooks.size() - 1);
        Book getBook = bookDao.getById(checkBook.getId());
        Assert.assertEquals(checkBook.getId(), getBook.getId());
    }

    @Test
    public void addTest() throws Exception {
        long preCount = bookDao.countAll();
        Book newBook = generateNewBook();
        bookDao.add(newBook);
        long afterCount = bookDao.countAll();

        Assert.assertEquals("하나 더 추가 되었는지?", preCount + 1, afterCount);
    }

    @Test
    public void deleteTest() throws Exception {
        List<Book> allBooks = bookDao.getAll();
        if (allBooks.isEmpty()) {
            bookDao.add(generateNewBook());
            allBooks = bookDao.getAll();
        }
        Book checkBook = allBooks.get(allBooks.size() - 1);
        bookDao.delete(checkBook);

        Book deletedBook = bookDao.getById(checkBook.getId());
        Assert.assertNull(deletedBook);
    }

    @Test
    public void getAll() throws Exception {
        List<Book> allBooks = bookDao.getAll();
        long allCount = bookDao.countAll();

        Assert.assertEquals(allBooks.size(), allCount);
    }

    @Test
    public void deleteAll() throws Exception {
        long allCount = bookDao.countAll();
        if (allCount <= 10) {
            for (int i = 0 ; i < 10 ; i++) {
                bookDao.add(generateNewBook());
            }
        }
        Assert.assertTrue(bookDao.countAll() >= 10);
        bookDao.deleteAll();
        Assert.assertEquals(bookDao.countAll(), 0);
    }

    @Test
    public void update() throws Exception {
        long allCount = bookDao.countAll();
        if (allCount == 0) {
            bookDao.add(generateNewBook());
        }
        Book preBook = bookDao.getAll().get(0);

        String preName = preBook.getName();
        preBook.setName(new StringBuilder(preName).reverse().toString());
        String preComment = preBook.getComment();
        preBook.setComment(new StringBuilder(preComment).reverse().toString());
        bookDao.update(preBook);

        Book afterBook = bookDao.getById(preBook.getId());
        assertThat(afterBook.getName()).isEqualTo(new StringBuilder(preName).reverse().toString());
        assertThat(afterBook.getComment()).isEqualTo(new StringBuilder(preComment).reverse().toString());
    }
}
