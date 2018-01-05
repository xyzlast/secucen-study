package com.xyzlast.bookstore.service;

import static org.junit.Assert.*;

import com.xyzlast.bookstore.config.BookStoreConfig;
import com.xyzlast.bookstore.dao.BookDao;
import com.xyzlast.bookstore.dao.UserDao;
import com.xyzlast.bookstore.entity.Book;
import com.xyzlast.bookstore.entity.BookStatus;
import com.xyzlast.bookstore.entity.User;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BookStoreConfig.class)
public class UserServiceImplTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Before
    public void setUp() throws Exception {
        UserService userService = applicationContext.getBean(UserService.class);
        assertThat(userService).isNotNull();
    }

    @Test
    public void rentTest() {
        UserService userService = applicationContext.getBean(UserService.class);
        UserDao userDao = applicationContext.getBean(UserDao.class);
        BookDao bookDao = applicationContext.getBean(BookDao.class);
        User user = userDao.getAll().get(0);
        Book book = bookDao.getAll().get(0);
        book.setBookStatus(BookStatus.CanRent);
        book.setRentUserId(null);
        bookDao.update(book);

        assertThat(userService.rent(user.getId(), book.getId())).isTrue();
//        assertThat(userService).isInstanceOf(UserServiceImpl.class);
    }
}
