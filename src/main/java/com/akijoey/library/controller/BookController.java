package com.akijoey.library.controller;

import com.akijoey.library.entity.Book;
import com.akijoey.library.service.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("/api/books")
    public List<Book> list() {
        return bookService.list();
    }

    @PostMapping("/api/book")
    public Book addOrUpdate(@RequestBody Book book) {
        bookService.addOrUpdate(book);
        return book;
    }

    @PostMapping("/api/delete")
    public void delete(@RequestBody Book book) {
        bookService.deleteById(book.getId());
    }

    @GetMapping("/api/category/{cid}/book")
    public List<Book> listByCategory(@PathVariable("cid") int cid) {
        if (cid != 0)
            return bookService.listByCategory(cid);
        return list();
    }
}
