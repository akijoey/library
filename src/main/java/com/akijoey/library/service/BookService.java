package com.akijoey.library.service;

import com.akijoey.library.entity.Book;
import com.akijoey.library.entity.Category;
import com.akijoey.library.repository.BookRepository;

import com.akijoey.library.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CategoryService categoryService;

    @Autowired
    FileUtil fileUtil;

    public Book getBookByIsbn(long isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    public boolean existsBookByIsbn(long isbn) {
        return bookRepository.existsByIsbn(isbn);
    }

    public void saveBook(Book book) {
        bookRepository.save(book);
    }

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
        return bookRepository.findListByCategory(pageable, category);
    }

    public List<Map<String, Object>> getTable(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return bookRepository.findTable(pageable);
    }

    public List<Map<String, Object>> getTableByCategory(int page, int size, int cid) {
        Category category = categoryService.getCategoryById(cid);
        Pageable pageable = PageRequest.of(page - 1, size);
        return bookRepository.findTableByCategory(pageable, category);
    }

    public Map<String, Object> getDetailByIsbn(long isbn) {
        return bookRepository.findDetailByIsbn(isbn);
    }

    public void insertOrUpdateBook(Map<String, Object> data) {
        Book book;
        long isbn = (Long)data.get("isbn");
        if (existsBookByIsbn(isbn)) {
            book = getBookByIsbn((Long)data.get("isbn"));
        } else {
            book = new Book();
            book.setIsbn((Long)data.get("isbn"));
        }
        book.setCover((String)data.get("cover"));
        book.setTitle((String)data.get("title"));
        book.setAuthor((String)data.get("author"));
        book.setPress((String)data.get("press"));
        book.setDate((String)data.get("date"));
        book.setPage((Integer)data.get("page"));
        book.setSummary((String)data.get("summary"));
        Category category = categoryService.getCategoryById((Integer)data.get("cid"));
        book.setCategory(category);
        saveBook(book);
    }

    public void changeCount(long isbn, int count) {
        Book book = getBookByIsbn(isbn);
        book.setCount(count);
        saveBook(book);
    }

    public String uploadCover(MultipartFile image) {
        return fileUtil.uploadImage(image);
    }

    public void deleteBookByIsbn(long isbn) {
        bookRepository.deleteByIsbn(isbn);
    }

}
