package com.xyzlast.bookstore.repository;

import com.xyzlast.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslJpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>, QuerydslPredicateExecutor<Book> {
    Book findByName(String name);
}
