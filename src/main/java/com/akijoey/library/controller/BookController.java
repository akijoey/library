package com.akijoey.library.controller;

import com.akijoey.library.service.BookService;

import com.akijoey.library.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        return resultUtil.successResult("Get Success", Map.of("list", list));
    }

    @GetMapping("/list/{page}/{size}/{cid}")
    public Map<String, Object> getList(@PathVariable("page") int page, @PathVariable("size") int size, @PathVariable("cid") int cid) {
        List<Map<String, Object>> list = bookService.getListByCategory(page, size, cid);
        return resultUtil.successResult("Get Success", Map.of("list", list));
    }

    @GetMapping("/table/{page}/{size}")
    public Map<String, Object> getTable(@PathVariable("page") int page, @PathVariable("size") int size) {
        List<Map<String, Object>> table = bookService.getTable(page, size);
        return resultUtil.successResult("Get Success", Map.of("table", table));
    }

    @GetMapping("/table/{page}/{size}/{cid}")
    public Map<String, Object> getTable(@PathVariable("page") int page, @PathVariable("size") int size, @PathVariable("cid") int cid) {
        List<Map<String, Object>> table = bookService.getTableByCategory(page, size, cid);
        return resultUtil.successResult("Get Success", Map.of("table", table));
    }

    @PostMapping("/detail")
    public Map<String, Object> getDetail(@RequestBody Map<String, Long> data) {
        Map<String, Object> detail = bookService.getDetailByIsbn(data.get("isbn"));
        return resultUtil.successResult("Get Success", Map.of("detail", detail));
    }

    @PostMapping("/insert")
    public Map<String, Object> insert(@RequestBody Map<String, Object> data) {
        bookService.insertOrUpdateBook(data);
        return resultUtil.successResult("Insert Success");
    }

    @PostMapping("/count")
    public Map<String, Object> count(@RequestBody Map<String, Object> data) {
        bookService.changeCount((Long)data.get("isbn"), (Integer)data.get("count"));
        return resultUtil.successResult("Count Success");
    }

    @PostMapping("/upload")
    public Map<String, Object> upload(@RequestParam("cover") MultipartFile image) {
        String cover = bookService.uploadCover(image);
        if (cover == null) {
            return resultUtil.customResult(500, "Upload Failure");
        }
        return resultUtil.successResult("Upload Success", Map.of("cover", cover));
    }

    @PostMapping("/update")
    public Map<String, Object> update(@RequestBody Map<String, Object> data) {
        bookService.insertOrUpdateBook(data);
        return resultUtil.successResult("Update Success");
    }

    @PostMapping("/delete")
    @Transactional
    public Map<String, Object> delete(@RequestBody Map<String, Long> data) {
        bookService.deleteBookByIsbn(data.get("isbn"));
        return resultUtil.successResult("Delete Success");
    }

}
