package com.akijoey.library.repository;

import com.akijoey.library.entity.Book;
import com.akijoey.library.entity.Category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findAllByCategory(Category category);
    List<Book> findAllByTitleLikeOrAndAuthorLike(String key1, String key2);
}
