package com.xyzlast.bookstore.dao;

import com.xyzlast.bookstore.entity.Book;
import com.xyzlast.bookstore.entity.History;
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

public class HistoryDaoTest {

    private HistoryDao historyDao;
    private BookDao bookDao;
    private UserDao userDao;

    @Before
    public void setUp() throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory("org.mariadb.jdbc.Driver",
            "jdbc:mysql://127.0.0.1:4306/bookstore", "root", "qwer12#$");
        historyDao = new HistoryDao(new SqlExecutor(connectionFactory));
        bookDao = new BookDao(new SqlExecutor(connectionFactory));
        userDao = new UserDao(new SqlExecutor(connectionFactory));
        assertThat(historyDao).isNotNull();

        bookDao.add(TestValueGenerator.generateNewBook());
        userDao.add(TestValueGenerator.generateNewUser());
    }

    @Test
    public void addOne() throws Exception {
        int preCount = historyDao.countAll();
        historyDao.add(TestValueGenerator.generateNewHistory(bookDao, userDao));
        int afterCount = historyDao.countAll();
        assertThat(afterCount).isEqualTo(preCount + 1);
    }

    @Test
    public void getAll() throws Exception {
        List<History> allHistories = historyDao.getAll();
        int allCount = historyDao.countAll();
        assertThat(allCount).isEqualTo(allHistories.size());
    }

    @Test
    public void getById() throws Exception {
        List<History> allHistories = historyDao.getAll();
        assertThat(allHistories.size()).isGreaterThan(0);
        History preHistory = allHistories.get(0);
        History afterHistory = historyDao.getById(preHistory.getId());
        assertThat(afterHistory).isNotNull();
        assertThat(preHistory.getId()).isEqualTo(afterHistory.getId());
        assertThat(preHistory.getBookId()).isEqualTo(afterHistory.getBookId());
        assertThat(preHistory.getUserId()).isEqualTo(afterHistory.getUserId());
    }

}
