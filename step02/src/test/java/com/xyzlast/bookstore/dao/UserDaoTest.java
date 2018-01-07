package com.xyzlast.bookstore.dao;

import com.xyzlast.bookstore.config.BookStoreConfig;
import com.xyzlast.bookstore.entity.User;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BookStoreConfig.class)
public class UserDaoTest {

    @Autowired
    private ApplicationContext applicationContext;
    private UserDao userDao;

    @Before
    public void setUp() {
        userDao = applicationContext.getBean(UserDao.class);
    }

    @Test
    public void countAll() throws Exception {
        int userCount = userDao.countAll();
        assertThat(userCount).isGreaterThanOrEqualTo(0);
    }

    @Test
    public void getAll() throws Exception {
        List<User> users = userDao.getAll();
        int userCount = userDao.countAll();
        assertThat(users).isNotNull();
        assertThat(userCount).isEqualTo(users.size());
    }

    @Test
    public void getById() throws Exception {
        List<User> users = userDao.getAll();
        assertThat(users.size()).isGreaterThan(0);
    }

    @Test
    public void addTest() throws Exception {
        long preCount = userDao.countAll();
        User newUser = TestValueGenerator.generateNewUser();
        userDao.add(newUser);
        long afterCount = userDao.countAll();

        Assert.assertEquals("하나 더 추가 되었는지?", preCount + 1, afterCount);
    }
}
