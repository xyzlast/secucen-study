package com.xyzlast.bookstore;

import com.xyzlast.bookstore.Book;
import com.xyzlast.bookstore.BookService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BookServiceTest {

    private BookService bookService;

    @Before
    public void setUp() {
        ConnectionFactory connectionFactory = new ConnectionFactory("org.mariadb.jdbc.Driver",
            "jdbc:mysql://127.0.0.1:4306/bookstore", "root", "qwer12#$");
        bookService = new BookService(connectionFactory);
    }

    private Book generateNewBook() {
        Book newBook = new Book();
        newBook.setName("newName01");
        newBook.setAuthor("newAuthor01");
        newBook.setComment("newComment01");
        newBook.setPublishDate(new Date());
        return newBook;
    }

    @Test
    public void getTest() throws Exception {
        List<Book> allBooks = bookService.getAll();
        if (allBooks.isEmpty()) {
            bookService.add(generateNewBook());
            allBooks = bookService.getAll();
        }
        Book checkBook = allBooks.get(allBooks.size() - 1);
        Book getBook = bookService.get(checkBook.getId());
        Assert.assertEquals(checkBook.getId(), getBook.getId());
    }

    @Test
    public void addTest() throws Exception {
        long preCount = bookService.countAll();
        Book newBook = generateNewBook();
        bookService.add(newBook);
        long afterCount = bookService.countAll();

        Assert.assertEquals("하나 더 추가 되었는지?", preCount + 1, afterCount);
    }

    @Test
    public void deleteTest() throws Exception {
        List<Book> allBooks = bookService.getAll();
        if (allBooks.isEmpty()) {
            bookService.add(generateNewBook());
            allBooks = bookService.getAll();
        }
        Book checkBook = allBooks.get(allBooks.size() - 1);
        bookService.delete(checkBook.getId());

        Book deletedBook = bookService.get(checkBook.getId());
        Assert.assertNull(deletedBook);
    }

    @Test
    public void getAll() throws Exception {
        List<Book> allBooks = bookService.getAll();
        long allCount = bookService.countAll();

        Assert.assertEquals(allBooks.size(), allCount);
    }

    @Test
    public void deleteAll() throws Exception {
        long allCount = bookService.countAll();
        if (allCount <= 10) {
            for (int i = 0 ; i < 10 ; i++) {
                bookService.add(generateNewBook());
            }
        }
        Assert.assertTrue(bookService.countAll() >= 10);
        bookService.deleteAll();
        Assert.assertEquals(bookService.countAll(), 0);
    }

    @Test
    public void update() throws Exception {
        long allCount = bookService.countAll();
        if (allCount == 0) {
            bookService.add(generateNewBook());
        }
        Book preBook = bookService.getAll().get(0);

        String preName = preBook.getName();
        preBook.setName(new StringBuilder(preName).reverse().toString());
        String preComment = preBook.getComment();
        preBook.setComment(new StringBuilder(preComment).reverse().toString());
        bookService.update(preBook);

        Book afterBook = bookService.get(preBook.getId());
        assertThat(afterBook.getName()).isEqualTo(new StringBuilder(preName).reverse().toString());
        assertThat(afterBook.getComment()).isEqualTo(new StringBuilder(preComment).reverse().toString());
    }
}
