package com.xyzlast.bookstore.service;

import com.xyzlast.bookstore.constant.UserLevel;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SuppressWarnings("unused")
public class UserLevelDeterminantImplTest {

    @Test
    public void determineLevel() {
        UserLevelDeterminantImpl userLevelDeterminant = new UserLevelDeterminantImpl();
        assertThat(userLevelDeterminant.determine(10)).isEqualTo(UserLevel.COMMON);
        assertThat(userLevelDeterminant.determine(100)).isEqualTo(UserLevel.VIP);
        assertThat(userLevelDeterminant.determine(300)).isEqualTo(UserLevel.VVIP);
    }

}
