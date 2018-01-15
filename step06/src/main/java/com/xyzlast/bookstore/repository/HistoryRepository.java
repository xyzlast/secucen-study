package com.xyzlast.bookstore.repository;

import com.xyzlast.bookstore.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<History, Integer>, QuerydslPredicateExecutor<History> {

}
