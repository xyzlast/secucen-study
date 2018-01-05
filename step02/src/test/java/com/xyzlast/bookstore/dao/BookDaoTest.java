package com.xyzlast.bookstore.dao;

import com.xyzlast.bookstore.config.BookStoreConfig;
import com.xyzlast.bookstore.entity.Book;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static com.xyzlast.bookstore.dao.TestValueGenerator.generateNewBook;
import static org.assertj.core.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BookStoreConfig.class)
public class BookDaoTest {

    @Autowired
    private ApplicationContext applicationContext;
    private BookDao bookDao;

    @Before
    public void setUp() {
        bookDao = applicationContext.getBean(BookDao.class);
    }

    @Test
    public void getTest() {
        List<Book> allBooks = bookDao.getAll();
        if (allBooks.isEmpty()) {
            bookDao.add(TestValueGenerator.generateNewBook());
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
        for (Book book : allBooks) {
            assertThat(book.getPublishDate()).isNotNull();
            assertThat(book.getName()).isNotBlank().isNotEmpty();
        }
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
        assertThat(bookDao.countAll()).isEqualTo(0);
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
        assertThat(bookDao.update(preBook)).isTrue();

        Book afterBook = bookDao.getById(preBook.getId());
        assertThat(afterBook.getName()).isEqualTo(new StringBuilder(preName).reverse().toString());
        assertThat(afterBook.getComment()).isEqualTo(new StringBuilder(preComment).reverse().toString());
    }
}
