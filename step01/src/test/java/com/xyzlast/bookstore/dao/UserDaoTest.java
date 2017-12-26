package com.xyzlast.bookstore.dao;

import com.xyzlast.bookstore.entity.User;
import com.xyzlast.bookstore.util.ConnectionFactory;
import com.xyzlast.bookstore.util.SqlExecutor;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SuppressWarnings("unused")
public class UserDaoTest {

    private UserDao userDao;

    @Before
    public void setUp() {
        ConnectionFactory connectionFactory = new ConnectionFactory("org.mariadb.jdbc.Driver",
            "jdbc:mysql://127.0.0.1:4306/bookstore", "root", "qwer12#$");
        userDao = new UserDao(new SqlExecutor(connectionFactory));
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

}
