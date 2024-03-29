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

    public long countByUser(User user) {
        return recordRepository.countByUser(user);
    }

    public long getCountByUsername(String username) {
        User user = userService.getUserByUsername(username);
        return countByUser(user);
    }

    public long getCountByUserId(int uid) {
        User user = userService.getUserById(uid);
        return countByUser(user);
    }

    public List<Map<String, Object>> getListByUser(int page, int size, User user) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return recordRepository.findListByUser(pageable, user);
    }

    public List<Map<String, Object>> getListByUsername(int page, int size, String username) {
        User user = userService.getUserByUsername(username);
        return getListByUser(page, size, user);
    }

    public List<Map<String, Object>> getListByUserId(int page, int size, int uid) {
        User user = userService.getUserById(uid);
        return getListByUser(page, size, user);
    }

    public long getTotal() {
        return recordRepository.count();
    }

    public List<Map<String, Object>> getTable(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return recordRepository.findTable(pageable);
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
