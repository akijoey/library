package com.akijoey.library.controller;

import com.akijoey.library.entity.Book;
import com.akijoey.library.service.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("/getList")
    public List<Book> list() {
        return bookService.list();
    }

    @PostMapping("/update")
    public Book addOrUpdate(@RequestBody Book book) {
        bookService.addOrUpdate(book);
        return book;
    }

    @PostMapping("/delete")
    public void delete(@RequestBody long isbn) {
        bookService.deleteByIsbn(isbn);
    }

    @GetMapping("/category/{cid}")
    public List<Book> listByCategory(@PathVariable("cid") int cid) {
        if (cid != 0)
            return bookService.listByCategory(cid);
        return list();
    }
}
