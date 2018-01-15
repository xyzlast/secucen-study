package com.xyzlast.bookstore.service;

import com.xyzlast.bookstore.entity.History;
import org.springframework.data.domain.Page;

public interface HistoryService {
    Page<History> listHistories(Integer bookId, Integer userId, int pageIndex, int pageSize);
}
