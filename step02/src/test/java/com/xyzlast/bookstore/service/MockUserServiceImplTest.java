package com.xyzlast.bookstore.service;

import static org.junit.Assert.*;

import com.xyzlast.bookstore.dao.BookDao;
import com.xyzlast.bookstore.dao.HistoryDao;
import com.xyzlast.bookstore.dao.UserDao;
import com.xyzlast.bookstore.entity.Book;
import com.xyzlast.bookstore.entity.BookStatus;
import com.xyzlast.bookstore.entity.User;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MockUserServiceImplTest {
    @Test
    public void BOOK상태가_정상적이지_않은_경우_테스트() throws Exception {
        Book book = new Book();
        //대여되어 있는 상태
        book.setBookStatus(BookStatus.RentNow);

        BookDao bookDao = mock(BookDao.class);
        when(bookDao.getById(any())).thenReturn(book);
        UserDao userDao = mock(UserDao.class);
        HistoryDao historyDao = mock(HistoryDao.class);

        UserServiceImpl userService = new UserServiceImpl(userDao, bookDao, historyDao);
        assertThat(userService.rent(0, 0)).isFalse();
    }

    @Test
    public void BOOK의_RENTUSERID가_NULL이_아닌경우_테스트() throws Exception {
        int targetUserId = 0;
        Book book = new Book();
        book.setBookStatus(BookStatus.CanRent);
        book.setRentUserId(0);

        BookDao bookDao = mock(BookDao.class);
        when(bookDao.getById(any())).thenReturn(book);
        UserDao userDao = mock(UserDao.class);
        HistoryDao historyDao = mock(HistoryDao.class);

        UserServiceImpl userService = new UserServiceImpl(userDao, bookDao, historyDao);
        assertThat(userService.rent(targetUserId, 0)).isFalse();
    }

    @Test
    public void 정상적인_RENT가_되는_경우_사용자Level이_변경되는상황_테스트() throws Exception {
        //User.point = 90, level이 1로 변경되는 것 확인
        testRentWithUserCondition(90, 0, 1);
        //User.point = 290, level이 2로 변경되는 것 확인
        testRentWithUserCondition(290, 1, 2);
        //User.point = 50, level이 0으로 유지되는 것 확인
        testRentWithUserCondition(50, 0, 0);
    }

    private void testRentWithUserCondition(int currentPoint, int currentLevel, int expectLevel) {
        Book book = new Book();
        book.setBookStatus(BookStatus.CanRent);
        book.setRentUserId(null);

        User user = new User();
        user.setPoint(currentPoint);
        user.setLevel(currentLevel);

        BookDao bookDao = mock(BookDao.class);
        when(bookDao.getById(any())).thenReturn(book);
        UserDao userDao = mock(UserDao.class);
        when(userDao.getById(any())).thenReturn(user);
        HistoryDao historyDao = mock(HistoryDao.class);

        UserServiceImpl userService = new UserServiceImpl(userDao, bookDao, historyDao);
        //Rent가 정상적으로 진행되는 것을 확인
        assertThat(userService.rent(0, 0)).isTrue();
        //Rent가 진행된 후, 사용자의 Level이 바뀐것을 확인
        assertThat(user.getLevel()).isEqualTo(expectLevel);

        //book, user가 update되는 코드가 호출되는 것을 확인
        verify(bookDao, times(1)).update(book);
        verify(userDao, times(1)).update(user);
        //History가 추가 되는것을 확인
        verify(historyDao, times(1)).add(any());
    }
}
