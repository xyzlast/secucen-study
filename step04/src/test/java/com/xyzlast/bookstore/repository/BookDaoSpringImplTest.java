package com.xyzlast.bookstore.repository;

import com.xyzlast.bookstore.config.BookStoreConfig;
import com.xyzlast.bookstore.entity.Book;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BookStoreConfig.class)
public class BookDaoSpringImplTest {
    @Autowired
    private BookDao bookDao;

    @Test
    @Transactional
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
    @Transactional
    public void addTest() {
        long preCount = bookDao.countAll();
        Book newBook = TestValueGenerator.generateNewBook();
        bookDao.add(newBook);
        long afterCount = bookDao.countAll();

        Assert.assertEquals("하나 더 추가 되었는지?", preCount + 1, afterCount);
    }

    @Test
    @Transactional
    public void deleteTest() {
        List<Book> allBooks = bookDao.getAll();
        if (allBooks.isEmpty()) {
            bookDao.add(TestValueGenerator.generateNewBook());
            allBooks = bookDao.getAll();
        }
        Book checkBook = allBooks.get(allBooks.size() - 1);
        bookDao.delete(checkBook);
        Book deletedBook = bookDao.getById(checkBook.getId());
        Assert.assertNull(deletedBook);
    }

    @Test
    @Transactional
    public void getAll() {
        List<Book> allBooks = bookDao.getAll();
        long allCount = bookDao.countAll();
        for (Book book : allBooks) {
            assertThat(book.getPublishDate()).isNotNull();
            assertThat(book.getName()).isNotBlank().isNotEmpty();
        }
        Assert.assertEquals(allBooks.size(), allCount);
    }

    @Test
    @Transactional
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

        Book afterBook = bookDao.getById(preBook.getId());
        assertThat(afterBook.getName()).isEqualTo(new StringBuilder(preName).reverse().toString());
        assertThat(afterBook.getComment()).isEqualTo(new StringBuilder(preComment).reverse().toString());
    }
}
