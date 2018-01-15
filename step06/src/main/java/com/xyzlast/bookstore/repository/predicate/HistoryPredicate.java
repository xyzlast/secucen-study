package com.xyzlast.bookstore.repository.predicate;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.xyzlast.bookstore.entity.QBook;
import com.xyzlast.bookstore.entity.QUser;

public class HistoryPredicate {
    private HistoryPredicate() {

    }

    public static Predicate buildQueryByBookIdAndUserId(Integer bookId, Integer userId) {
        BooleanBuilder queryBuilder = new BooleanBuilder();
        if (bookId != null) {
            queryBuilder.and(QBook.book.id.eq(bookId));
        }
        if (userId != null) {
            queryBuilder.and(QUser.user.id.eq(userId));
        }
        return queryBuilder;
    }
}
