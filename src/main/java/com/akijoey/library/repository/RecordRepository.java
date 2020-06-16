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

    @Query(value = "select r.id as id, b.isbn as isbn, b.title as name, r.borrowing as borrow, r.returning as return, r.state as state from Record r, User u, Book b where r.user.id = u.id and r.book.isbn = b.isbn and u.username = :username")
    List<Map<String, Object>> findTableByUsername(@Param("username") String username, Pageable pageable);

}
