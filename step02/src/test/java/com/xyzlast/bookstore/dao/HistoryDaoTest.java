package com.xyzlast.bookstore.dao;

import com.xyzlast.bookstore.config.BookStoreConfig;
import com.xyzlast.bookstore.entity.History;
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
public class HistoryDaoTest {

    @Autowired
    private ApplicationContext applicationContext;
    private HistoryDao historyDao;
    private BookDao bookDao;
    private UserDao userDao;

    @Before
    public void setUp() throws Exception {
        historyDao = applicationContext.getBean(HistoryDao.class);
        assertThat(historyDao).isNotNull();
        bookDao = applicationContext.getBean(BookDao.class);
        assertThat(bookDao).isNotNull();
        userDao = applicationContext.getBean(UserDao.class);
        assertThat(userDao).isNotNull();
    }

    @Test
    public void addOne() throws Exception {
        int preCount = historyDao.countAll();
        historyDao.add(TestValueGenerator.generateNewHistory(bookDao, userDao));
        int afterCount = historyDao.countAll();
        assertThat(afterCount).isEqualTo(preCount + 1);
    }

    @Test
    public void getAll() {
        List<History> allHistories = historyDao.getAll();
        int allCount = historyDao.countAll();
        assertThat(allCount).isEqualTo(allHistories.size());
    }

    @Test
    public void getById() {
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
