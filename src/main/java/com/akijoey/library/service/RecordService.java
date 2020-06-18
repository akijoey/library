package com.akijoey.library.service;

import com.akijoey.library.entity.Book;
import com.akijoey.library.entity.Record;
import com.akijoey.library.entity.User;
import com.akijoey.library.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RecordService {

    @Autowired
    RecordRepository recordRepository;

    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    public Record getRecordById(int id) {
        return recordRepository.findById(id);
    }

    public void saveRecord(Record record) {
        recordRepository.save(record);
    }

    public long getTotalByUsername(String username) {
        User user = userService.getUserByUsername(username);
        return recordRepository.countByUser(user);
    }

    public List<Map<String, Object>> getTableByUsername(String username, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return recordRepository.findTableByUsername(username, pageable);
    }

    public void insertRecord(String username, long isbn) {
        Record record = new Record();
        User user = userService.getUserByUsername(username);
        record.setUser(user);
        Book book = bookService.getBookByIsbn(isbn);
        book.setCount(book.getCount() - 1);
        bookService.saveBook(book);
        record.setBook(book);
        long timestamp = System.currentTimeMillis();
        record.setBorrowing(new Date(timestamp));
        record.setReturning(new Date(timestamp + 2592000000L));
        saveRecord(record);
    }

    public void updateState(int id) {
        Record record = getRecordById(id);
        Book book = record.getBook();
        if (record.isState()) {
            book.setCount(book.getCount() - 1);
        } else {
            book.setCount(book.getCount() + 1);
        }
        bookService.saveBook(book);
        record.setState(!record.isState());
        saveRecord(record);
    }

    public void updateReturn(int id, long timestamp) {
        Record record = getRecordById(id);
        record.setReturning(new Date(timestamp));
        saveRecord(record);
    }

    public void deleteRecordById(int id) {
        Record record = getRecordById(id);
        if (!record.isState()) {
            Book book = record.getBook();
            book.setCount(book.getCount() + 1);
            bookService.saveBook(book);
        }
        recordRepository.deleteById(id);
    }

}
