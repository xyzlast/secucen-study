package com.xyzlast.bookstore.entity;

import com.xyzlast.bookstore.repository.BookDao;
import com.xyzlast.bookstore.repository.UserDao;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SuppressWarnings("unused")
public class UserTest {

    @Test
    public void rentTest() {
        UserDao userDao = mock(UserDao.class);
        BookDao bookDao = mock(BookDao.class);

        User user = new User();
        user.setPoint(100);
        Book book = new Book();

        user.rentBook(book, 10, bookDao, userDao);

        //book에 대여자가 정상적으로 설정되었는지 확인
        assertThat(book.getRentUser()).isEqualTo(user);
        //user에 정상적으로 Point 값이 설정되었는지 확인
        assertThat(user.getPoint()).isEqualTo(110);
        //Repository를 통해서 객체 값이 업데이트가 되고 있는지 체크 필요
        verify(userDao, times(1)).update(user);
        verify(bookDao, times(1)).update(book);
    }

}
