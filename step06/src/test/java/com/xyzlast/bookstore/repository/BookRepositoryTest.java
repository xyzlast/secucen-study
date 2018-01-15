package com.xyzlast.bookstore.repository;

import com.xyzlast.bookstore.config.BookStoreConfig;
import com.xyzlast.bookstore.entity.Book;
import com.xyzlast.bookstore.entity.QBook;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BookStoreConfig.class)
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void countAll() {
        long count = bookRepository.count();
        assertThat(count).isGreaterThan(0);
    }

    @Test
    @Transactional
    public void getTest() {
        List<Book> allBooks = bookRepository.findAll();
        if (allBooks.isEmpty()) {
            bookRepository.save(TestValueGenerator.generateNewBook());
            allBooks = bookRepository.findAll();
        }
        Book checkBook = allBooks.get(allBooks.size() - 1);
        Book getBook = bookRepository.findById(checkBook.getId()).get();
        Assert.assertEquals(checkBook.getId(), getBook.getId());
    }

    @Test
    @Transactional
    public void addTest() throws Exception {
        long preCount = bookRepository.count();
        Book newBook = TestValueGenerator.generateNewBook();
        bookRepository.save(newBook);
        long afterCount = bookRepository.count();
        Assert.assertEquals("하나 더 추가 되었는지?", preCount + 1, afterCount);
    }

    @Test
    public void findByName01() {
        QBook qBook = QBook.book;
        Optional<Book> bookOptional = bookRepository.findOne(qBook.name.eq("ykyoon"));
        if (bookOptional.isPresent()) {
            System.out.println(bookOptional.get());
        }

        Book book = bookRepository.findByName("ykyoon");
        System.out.println(book);
    }

//    @Test
//    @Transactional
//    public void deleteTest() throws Exception {
//        List<Book> allBooks = bookDao.getAll();
//        if (allBooks.isEmpty()) {
//            bookDao.add(TestValueGenerator.generateNewBook());
//            allBooks = bookDao.getAll();
//        }
//        Book checkBook = allBooks.get(allBooks.size() - 1);
//        bookDao.delete(checkBook);
//    }
//
//    @Test
//    @Transactional
//    public void getAll() throws Exception {
//        List<Book> allBooks = bookDao.getAll();
//        long allCount = bookDao.countAll();
//        for (Book book : allBooks) {
//            assertThat(book.getPublishDate()).isNotNull();
//            assertThat(book.getName()).isNotBlank().isNotEmpty();
//        }
//        Assert.assertEquals(allBooks.size(), allCount);
//    }
//
////    @Test
////    public void deleteAll() throws Exception {
////        long allCount = bookDao.countAll();
////        if (allCount <= 10) {
////            for (int i = 0 ; i < 10 ; i++) {
////                bookDao.add(TestValueGenerator.generateNewBook());
////            }
////        }
////        Assert.assertTrue(bookDao.countAll() >= 10);
////        bookDao.deleteAll();
////        assertThat(bookDao.countAll()).isEqualTo(0);
////    }
//
//    @Test
//    @Transactional
//    public void update() {
////        System.out.println(TransactionSynchronizationManager.isActualTransactionActive());
//        long allCount = bookDao.countAll();
//        if (allCount == 0) {
//            bookDao.add(TestValueGenerator.generateNewBook());
//        }
//        Book preBook = bookDao.getAll().get(0);
//
//        String preName = preBook.getName();
//        preBook.setName(new StringBuilder(preName).reverse().toString());
//        String preComment = preBook.getComment();
//        preBook.setComment(new StringBuilder(preComment).reverse().toString());
//        bookDao.update(preBook);
//
//        Book afterBook = bookDao.getById(preBook.getId());
//        assertThat(afterBook.getName()).isEqualTo(new StringBuilder(preName).reverse().toString());
//        assertThat(afterBook.getComment()).isEqualTo(new StringBuilder(preComment).reverse().toString());
//    }
}
