package com.xyzlast.bookstore.service;

import com.xyzlast.bookstore.dao.BookDao;
import com.xyzlast.bookstore.dao.HistoryDao;
import com.xyzlast.bookstore.dao.UserDao;
import com.xyzlast.bookstore.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private BookDao bookDao;
    private HistoryDao historyDao;

    @Autowired
    public UserServiceImpl(UserDao userDao, BookDao bookDao, HistoryDao historyDao) {
        this.userDao = userDao;
        this.bookDao = bookDao;
        this.historyDao = historyDao;
    }

    @Override
    @Transactional
    public boolean rent(int userId, int bookId) {
        User user = userDao.getById(userId);
        Book book = bookDao.getById(bookId);
        if (book.getBookStatus() != BookStatus.CanRent || book.getRentUserId() != null) {
            throw new RuntimeException("BOOK의 상태가 정상적이지 않습니다.");
        }

        user.setPoint(user.getPoint() + 10);
        if (user.getPoint() >= 100 && user.getPoint() < 300) {
            user.setLevel(1);
        } else if (user.getPoint() >= 300) {
            user.setLevel(2);
        } else {
            user.setLevel(0);
        }
        book.setRentUserId(userId);
        book.setBookStatus(BookStatus.RentNow);

        userDao.update(user);
        bookDao.update(book);

        //NOTE: History 기록
        History history = new History();
        history.setDate(new Date());
        history.setActionType(HistoryActionType.RENT_BOOK);
        history.setUserId(userId);
        history.setBookId(bookId);
        historyDao.add(history);

        return true;
    }

    @Override
    public boolean returnBook(int userId, int bookId) {
        Book book = bookDao.getById(bookId);
        if (book.getBookStatus() != BookStatus.RentNow || book.getRentUserId().equals(userId)) {
            return false;
        }
        book.setRentUserId(null);
        book.setBookStatus(BookStatus.CanRent);
        bookDao.update(book);

        History history = new History();
        history.setDate(new Date());
        history.setActionType(HistoryActionType.RENT_BOOK);
        history.setUserId(userId);
        history.setBookId(bookId);
        historyDao.add(history);

        return true;
    }

    @Override
    public List<User> listup() {
        return null;
    }

    @Override
    public List<History> getHistories(int userId) {
        return null;
    }
}
