package com.xyzlast.web.controller;

import com.xyzlast.web.view.BookListExcelView;
import com.xyzlast.web.view.BookListIText5PdfView;
import com.xyzlast.web.view.BookListITextPdfView;
import com.xyzlast.web.vo.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Controller
public class BookController {

    private final BookListExcelView bookListExcelView;
    private final BookListIText5PdfView bookListPdf5View;
    private final BookListITextPdfView bookListPdfView;

    @Autowired
    public BookController(BookListExcelView bookListExcelView, BookListITextPdfView bookListPdfView,
                          BookListIText5PdfView bookListPdf5View) {
        this.bookListExcelView = bookListExcelView;
        this.bookListPdfView = bookListPdfView;
        this.bookListPdf5View = bookListPdf5View;
    }

    @GetMapping(value = "/book/itext2/pdf")
    public ModelAndView getBookPdf() {
        List<Book> books = getBooks();
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setView(this.bookListPdfView);
        modelAndView.addObject("books", books);
        return modelAndView;
    }

    @GetMapping(value = "/book/itext5/pdf")
    public ModelAndView getBookPdfWithIText5() {
        List<Book> books = getBooks();
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setView(this.bookListPdf5View);
        modelAndView.addObject("books", books);
        return modelAndView;
    }

    @GetMapping(value = "/book/excel")
    public ModelAndView getBookExcel() {
        List<Book> books = getBooks();
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setView(this.bookListExcelView);
        modelAndView.addObject("books", books);
        return modelAndView;
    }

    @GetMapping(value = "/book/json")
    @ResponseBody
    public Object getBookJson() {
        List<Book> books = getBooks();
        return books;
    }

    private String getRandomBookStatus() {
        int randomNum = ThreadLocalRandom.current().nextInt(0, 3);
        int index = randomNum % 3;
        String[] items = {
            "NORMAL",
            "RENTNOW",
            "MISSING"
        };
        return items[index];
    }

    @RequestMapping(value = "/book/list", method = RequestMethod.GET)
    public ModelAndView getBookList(@RequestParam(required = false) String type) {
        List<Book> books = getBooks();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("book/list");
        modelAndView.addObject("books", books);
        modelAndView.addObject("date", new Date());
        modelAndView.addObject("number", 123467.412394);
        return modelAndView;
    }

    private List<Book> getBooks() {
        List<Book> books = new ArrayList<>();
        for (int i = 0 ; i < 10 ; i++) {
            String name = String.format("책%d", i);
            String author = String.format("저자%d", i);
            String status = "상태";
            books.add(new Book(name, author, getRandomBookStatus()));
        }
        return books;
    }
}
