package com.xyzlast.web.controller;

import com.xyzlast.web.config.RootConfiguration;
import com.xyzlast.web.view.BookListExcelView;
import com.xyzlast.web.view.BookListIText5PdfView;
import com.xyzlast.web.view.BookListITextPdfView;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
    RootConfiguration.class
})
@WebAppConfiguration
public class BookControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getBookList() throws Exception {
        mockMvc.perform(get("/book/list").param("type", "ftl"))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();
    }

    @Test
    public void getBookJson() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/book/json"))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();
    }

    @Test
    public void getBookExcel() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/book/excel"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("books"))
            .andDo(MockMvcResultHandlers.print())
            .andReturn();
        assertThat(mvcResult.getModelAndView().getView()).isNotNull().isInstanceOf(BookListExcelView.class);
    }

    @Test
    public void getBookItext2Pdf() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/book/itext2/pdf"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("books"))
            .andDo(MockMvcResultHandlers.print())
            .andReturn();
        assertThat(mvcResult.getModelAndView().getView()).isNotNull().isInstanceOf(BookListITextPdfView.class);
        Files.write(Paths.get("sample.pdf"), mvcResult.getResponse().getContentAsByteArray());
    }

    @Test
    public void getBookItext5Pdf() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/book/itext5/pdf"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("books"))
            .andDo(MockMvcResultHandlers.print())
            .andReturn();
        assertThat(mvcResult.getModelAndView().getView()).isNotNull().isInstanceOf(BookListIText5PdfView.class);
        Files.write(Paths.get("sample.pdf"), mvcResult.getResponse().getContentAsByteArray());

    }
}
