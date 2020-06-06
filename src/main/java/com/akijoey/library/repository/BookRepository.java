package com.akijoey.library.repository;

import com.akijoey.library.entity.Book;
import com.akijoey.library.entity.Category;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    Book findByIsbn(long isbn);

    void deleteByIsbn(long isbn);

    long countByCategory(Category category);

    @Query(value = "select isbn as isbn, cover as cover, title as title, author as author from Book")
    List<Map<String, Object>> findList(Pageable pageable);

    @Query(value = "select isbn as isbn, cover as cover, title as title, author as author from Book where category = :category")
    List<Map<String, Object>> findListByCategory(@Param("category") Category category, Pageable pageable);

    @Query(value = "select isbn as isbn, title as title, author as author, press as press, date as data, page as page from Book")
    List<Map<String, Object>> findTable(Pageable pageable);

    @Query(value = "select isbn as isbn, title as title, author as author, press as press, date as data, page as page from Book where category = :category")
    List<Map<String, Object>> findTableByCategory(@Param("category") Category category, Pageable pageable);

}
