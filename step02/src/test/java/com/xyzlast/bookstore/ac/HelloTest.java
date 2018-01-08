package com.xyzlast.bookstore.ac;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SuppressWarnings("unused")
public class HelloTest {
    @Test
    public void registerApplicationContext() {
        StaticApplicationContext ac = new StaticApplicationContext();
        ac.registerSingleton("hello1", Hello.class);
        Hello hello = ac.getBean("hello1", Hello.class);
        assertThat(hello).isNotNull();
        Hello hello2 = ac.getBean("hello1", Hello.class);
        assertThat(hello).isSameAs(hello2);
    }

    @Test
    public void registerApplicationContextWithPrototype() {
        StaticApplicationContext ac = new StaticApplicationContext();
        ac.registerPrototype("hello1", Hello.class);
        Hello hello = ac.getBean("hello1", Hello.class);
        assertThat(hello).isNotNull();
        Hello hello2 = ac.getBean("hello1", Hello.class);
        assertThat(hello).isNotSameAs(hello2);
    }
}
