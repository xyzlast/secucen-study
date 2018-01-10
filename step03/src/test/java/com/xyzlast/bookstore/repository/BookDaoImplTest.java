package com.xyzlast.bookstore.repository;

import static org.junit.Assert.*;

import com.xyzlast.bookstore.config.BookStoreConfig;
import com.xyzlast.bookstore.entity.Book;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BookStoreConfig.class)
public class BookDaoImplTest {

    @Autowired
    private ApplicationContext applicationContext;
    private BookDao bookDao;

    @Before
    public void setUp() {
        bookDao = applicationContext.getBean(BookDao.class);
        assertThat(bookDao).isNotNull();
    }

    @Test
    public void countAll() {
        int bookCount = bookDao.countAll();
        assertThat(bookCount).isGreaterThan(0);
    }

    @Test
    public void getAll() {
        List<Book> books = bookDao.getAll();
        for (Book book : books) {
            System.out.println(book);
        }
    }

    @Test
    public void addTest() {
        long preCount = bookDao.countAll();
        Book newBook = TestValueGenerator.generateNewBook();
        bookDao.add(newBook);
        long afterCount = bookDao.countAll();
        Assert.assertEquals("하나 더 추가 되었는지?", preCount + 1, afterCount);
    }

    @Test
    public void update() {
        long allCount = bookDao.countAll();
        if (allCount == 0) {
            bookDao.add(TestValueGenerator.generateNewBook());
        }
        Book preBook = bookDao.getAll().get(0);

        String preName = preBook.getName();
        preBook.setName(new StringBuilder(preName).reverse().toString());
        String preComment = preBook.getComment();
        preBook.setComment(new StringBuilder(preComment).reverse().toString());
        assertThat(bookDao.update(preBook)).isEqualTo(1);

        Book afterBook = bookDao.getById(preBook.getId());
        assertThat(afterBook.getName()).isEqualTo(new StringBuilder(preName).reverse().toString());
        assertThat(afterBook.getComment()).isEqualTo(new StringBuilder(preComment).reverse().toString());
    }
}
