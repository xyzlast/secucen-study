package com.xyzlast.bookstore.repository;

import com.xyzlast.bookstore.entity.Book;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;

import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookDaoTest {

    private SqlSessionFactory sqlSessionFactory;
    SqlSession sqlSession;
    private BookDao bookDao;

    @Before
    public void setUp() throws Exception {
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        sqlSession = sqlSessionFactory.openSession();
        bookDao = sqlSession.getMapper(BookDao.class);
    }
    @After
    public void tearDown() {
        if (sqlSession != null) {
            sqlSession.close();
        }
    }
    @Test
    public void countAll() throws Exception {
        int bookCount = bookDao.countAll();
        assertThat(bookCount).isGreaterThan(0);
    }

    @Test
    public void getAll() throws Exception {
        List<Book> books = bookDao.getAll();
        for (Book book : books) {
            System.out.println(book);
        }
    }

    @Test
    public void addTest() throws Exception {
        long preCount = bookDao.countAll();
        Book newBook = TestValueGenerator.generateNewBook();
        bookDao.add(newBook);
        long afterCount = bookDao.countAll();
        Assert.assertEquals("하나 더 추가 되었는지?", preCount + 1, afterCount);
    }

    @Test
    public void update() throws Exception {
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
