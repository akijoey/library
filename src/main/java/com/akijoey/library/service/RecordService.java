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
        Book book = bookService.getBookByIsbn(isbn);
        record.setUser(user);
        record.setBook(book);
        long timestamp = System.currentTimeMillis();
        record.setBorrowing(new Date(timestamp));
        record.setReturning(new Date(timestamp + 2592000000L));
        recordRepository.save(record);
    }

    public void deleteRecordById(int id) {
        recordRepository.deleteById(id);
    }

    public void updateState(int id) {
        Record record = getRecordById(id);
        record.setState(true);
        recordRepository.save(record);
    }

    public void updateReturn(int id, long timestamp) {
        Record record = getRecordById(id);
        record.setReturning(new Date(timestamp));
        recordRepository.save(record);
    }

}
