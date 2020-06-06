package com.akijoey.library.service;

import com.akijoey.library.entity.Book;
import com.akijoey.library.entity.Category;
import com.akijoey.library.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CategoryService categoryService;

    public long getTotal() {
        return bookRepository.count();
    }

    public long getTotalByCategory(int cid) {
        Category category = categoryService.getCategoryById(cid);
        return bookRepository.countByCategory(category);
    }

    public List<Map<String, Object>> getList(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return bookRepository.findList(pageable);
    }

    public List<Map<String, Object>> getListByCategory(int page, int size, int cid) {
        Category category = categoryService.getCategoryById(cid);
        Pageable pageable = PageRequest.of(page - 1, size);
        return bookRepository.findListByCategory(category, pageable);
    }

    public List<Map<String, Object>> getTable(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return bookRepository.findTable(pageable);
    }

    public List<Map<String, Object>> getTableByCategory(int page, int size, int cid) {
        Category category = categoryService.getCategoryById(cid);
        Pageable pageable = PageRequest.of(page - 1, size);
        return bookRepository.findTableByCategory(category, pageable);
    }

    public Book getBookByIsbn(long isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    public void deleteBookByIsbn(long isbn) {
        bookRepository.deleteByIsbn(isbn);
    }

    public void insertOrUpdateBook(Book book) {
        bookRepository.save(book);
    }

}
