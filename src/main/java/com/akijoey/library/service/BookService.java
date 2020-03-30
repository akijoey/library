package com.akijoey.library.service;

import com.akijoey.library.entity.Book;
import com.akijoey.library.entity.Category;
import com.akijoey.library.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CategoryService categoryService;

    public List<Book> list() {
        return bookRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public void addOrUpdate(Book book) {
        bookRepository.save(book);
    }

    public void deleteById(int id) {
        bookRepository.deleteById(id);
    }

    public List<Book> listByCategory(int cid) {
        Category category = categoryService.get(cid);
        return bookRepository.findAllByCategory(category);
    }
}
