package com.xyzlast.bookstore.service;

import com.xyzlast.bookstore.constant.UserLevel;

public interface UserLevelDeterminant {
    UserLevel determine(int userPoint);
}
