import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class BookServiceTest {

    private BookService bookService;

    @Before
    public void setUp() {
        bookService = new BookService();
    }

    private Book generateNewBook() {
        Book newBook = new Book();
        newBook.setName("newName01");
        newBook.setAuthor("newAuthor01");
        newBook.setComment("newComment01");
        newBook.setPublishDate(new Date());
        return newBook;
    }

    @Test
    public void getTest() throws Exception {
        List<Book> allBooks = bookService.getAll();
        if (allBooks.isEmpty()) {
            bookService.add(generateNewBook());
            allBooks = bookService.getAll();
        }

        Book checkBook = allBooks.get(allBooks.size() - 1);
        Book getBook = bookService.get(checkBook.getId());
        Assert.assertEquals(checkBook.getId(), getBook.getId());
    }

    @Test
    public void addTest() throws Exception {
        long preCount = bookService.countAll();
        Book newBook = generateNewBook();
        bookService.add(newBook);
        long afterCount = bookService.countAll();

        Assert.assertEquals("하나 더 추가 되었는지?", preCount + 1, afterCount);
    }

    @Test
    public void deleteTest() throws Exception {
        List<Book> allBooks = bookService.getAll();
        if (allBooks.isEmpty()) {
            bookService.add(generateNewBook());
            allBooks = bookService.getAll();
        }
        Book checkBook = allBooks.get(allBooks.size() - 1);
        bookService.delete(checkBook.getId());

        Book deletedBook = bookService.get(checkBook.getId());
        Assert.assertNull(deletedBook);
    }

    @Test
    public void getAll() throws Exception {
        List<Book> allBooks = bookService.getAll();
        long allCount = bookService.countAll();

        Assert.assertEquals(allBooks.size(), allCount);
    }

    @Test
    public void deleteAll() throws Exception {
        long allCount = bookService.countAll();
        if (allCount <= 10) {
            for (int i = 0 ; i < 10 ; i++) {
                bookService.add(generateNewBook());
            }
        }
        Assert.assertTrue(bookService.countAll() >= 10);
        bookService.deleteAll();
        Assert.assertEquals(bookService.countAll(), 0);
    }
}
