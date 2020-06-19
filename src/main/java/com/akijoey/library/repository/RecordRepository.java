package com.akijoey.library.repository;

import com.akijoey.library.entity.Record;
import com.akijoey.library.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RecordRepository extends JpaRepository<Record, Integer> {

    Record findById(int id);

    void deleteById(int id);

    long countByUser(User user);

    @Query(value = "select id as id, book.isbn as isbn, book.title as name, borrowing as borrow, returning as return, state as state from Record where user = :user")
    List<Map<String, Object>> findListByUser(Pageable pageable, @Param("user") User user);

    @Query(value = "select id as id, user.id as uid, user.username as username, book.isbn as isbn, book.title as bookname, borrowing as borrow, returning as return, state as state from Record")
    List<Map<String, Object>> findTable(Pageable pageable);

}
