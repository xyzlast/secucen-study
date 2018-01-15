package com.xyzlast.bookstore.service;

import com.querydsl.core.BooleanBuilder;
import com.xyzlast.bookstore.entity.History;
import com.xyzlast.bookstore.entity.QBook;
import com.xyzlast.bookstore.entity.QUser;
import com.xyzlast.bookstore.repository.HistoryRepository;
import com.xyzlast.bookstore.repository.predicate.HistoryPredicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;

    public HistoryServiceImpl(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Transactional
    @Override
    public Page<History> listHistories(Integer bookId, Integer userId, int pageIndex, int pageSize) {
        return historyRepository.findAll(HistoryPredicate.buildQueryByBookIdAndUserId(bookId, userId),
            PageRequest.of(pageIndex, pageSize));
    }
}
