package com.akijoey.library.controller;

import com.akijoey.library.entity.Book;
import com.akijoey.library.service.BookService;

import com.akijoey.library.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    BookService bookService;

    @Autowired
    ResultUtil resultUtil;

    @GetMapping("/total")
    public Map<String, Object> getTotal() {
        long total = bookService.getTotal();
        return resultUtil.successResult("Get Success", Map.of("total", total));
    }

    @GetMapping("/total/{cid}")
    public Map<String, Object> getTotal(@PathVariable("cid") int cid) {
        long total = bookService.getTotalByCategory(cid);
        return resultUtil.successResult("Get Success", Map.of("total", total));
    }

    @GetMapping("/list/{page}/{size}")
    public Map<String, Object> getList(@PathVariable("page") int page, @PathVariable("size") int size) {
        List<Map<String, Object>> list = bookService.getList(page, size);
        return resultUtil.successResult("Get Success", list);
    }

    @GetMapping("/list/{page}/{size}/{cid}")
    public Map<String, Object> getList(@PathVariable("page") int page, @PathVariable("size") int size, @PathVariable("cid") int cid) {
        List<Map<String, Object>> list = bookService.getListByCategory(page, size, cid);
        return resultUtil.successResult("Get Success", list);
    }

    @GetMapping("/table")
    public Map<String, Object> getTable() {
        List<Map<String, Object>> list = bookService.getTable();
        return resultUtil.successResult("Get Success", list);
    }

    @GetMapping("/table/{cid}")
    public Map<String, Object> getTable(@PathVariable("cid") int cid) {
        List<Map<String, Object>> list = bookService.getTableByCategory(cid);
        return resultUtil.successResult("Get Success", list);
    }

    @PostMapping("/detail")
    public Map<String, Object> getDetail(@RequestBody long isbn) {
        Book detail = bookService.getBookByIsbn(isbn);
        return resultUtil.successResult("Get Success", detail);
    }

    @PostMapping("/insert")
    public Map<String, Object> insert(@RequestBody Book book) {
        bookService.insertOrUpdateBook(book);
        return resultUtil.successResult("Insert Success");
    }

    @PostMapping("/update")
    public Map<String, Object> update(@RequestBody Book book) {
        bookService.insertOrUpdateBook(book);
        return resultUtil.successResult("Update Success");
    }

    @PostMapping("/delete")
    public Map<String, Object> delete(@RequestBody long isbn) {
        bookService.deleteBookByIsbn(isbn);
        return resultUtil.successResult("Delete Success");
    }

}
