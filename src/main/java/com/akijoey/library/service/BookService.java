package com.akijoey.library.service;

import com.akijoey.library.entity.Book;
import com.akijoey.library.entity.Category;
import com.akijoey.library.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CategoryService categoryService;

    public List<Map<String, Object>> getList() {
        return bookRepository.findList();
    }

    public List<Map<String, Object>> getListByCategory(int cid) {
        Category category = categoryService.getCategoryById(cid);
        return bookRepository.findListByCategory(category);
    }

    public List<Map<String, Object>> getTable() {
        return bookRepository.findTable();
    }

    public List<Map<String, Object>> getTableByCategory(int cid) {
        Category category = categoryService.getCategoryById(cid);
        return bookRepository.findTableByCategory(category);
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
