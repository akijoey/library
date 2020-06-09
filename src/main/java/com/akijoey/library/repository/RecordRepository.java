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

    @Query(value = "select r.id, b.isbn, title name, borrowing borrow, returning 'return' from record r, user u, book b where r.uid = u.id and r.isbn = b.isbn and username = :username", nativeQuery = true)
    List<Map<String, Object>> findTableByUsername(@Param("username") String username, Pageable pageable);

}
