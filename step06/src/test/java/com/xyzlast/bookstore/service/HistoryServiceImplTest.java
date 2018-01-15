package com.xyzlast.bookstore.service;


import com.xyzlast.bookstore.config.BookStoreConfig;
import com.xyzlast.bookstore.entity.History;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
public class HistoryServiceImplTest {
    @Autowired
    private HistoryService historyService;

    @Test
    public void listHistories() throws Exception {
        Page<History> histories = historyService.listHistories(null, null, 0, 100);

    }
}
