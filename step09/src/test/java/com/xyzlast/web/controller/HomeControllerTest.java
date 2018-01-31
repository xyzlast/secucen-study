package com.xyzlast.web.controller;

import com.xyzlast.web.config.RootConfiguration;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import static org.assertj.core.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({
//    "file:src/main/webapp/WEB-INF/applicationContext.xml",
//    "file:src/main/webapp/WEB-INF/spring-servlet.xml"
//})
@ContextConfiguration(classes = RootConfiguration.class)
@WebAppConfiguration
public class HomeControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void helloWithoutNameTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/hello"))
                                     .andDo(print())
                                     .andExpect(status().isOk())
                                     .andExpect(model().attributeExists("message"))
                                     .andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertThat(modelAndView.getViewName()).isEqualTo("hello");
        assertThat(modelAndView.getModel().containsKey("message")).isTrue();
        assertThat(modelAndView.getModel().get("message")).isEqualTo("이름이 입력되지 않았습니다.");
    }

    @Test
    public void helloWithNameTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/hello.do").param("name", "윤영권"))
            .andExpect(status().isOk())
            .andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertThat(modelAndView.getViewName()).isEqualTo("hello");
        assertThat(modelAndView.getModel().containsKey("message")).isTrue();
        assertThat(modelAndView.getModel().get("message")).isEqualTo("Hello World!! 윤영권");

    }
}
