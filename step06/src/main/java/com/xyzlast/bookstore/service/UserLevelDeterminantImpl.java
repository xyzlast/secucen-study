package com.xyzlast.bookstore.service;

import com.xyzlast.bookstore.constant.UserLevel;
import org.springframework.stereotype.Service;

@Service
public class UserLevelDeterminantImpl implements UserLevelDeterminant {
    @Override
    public UserLevel determine(int userPoint) {
        if (userPoint >= 100 && userPoint < 300) {
            return UserLevel.VIP;
        } else if (userPoint >= 300) {
            return UserLevel.VVIP;
        }
        return UserLevel.COMMON;
    }
}
