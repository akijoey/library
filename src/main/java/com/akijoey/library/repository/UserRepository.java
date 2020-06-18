package com.akijoey.library.repository;

import com.akijoey.library.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findById(int id);

    void deleteById(int id);

    User findByUsername(String username);

    boolean existsByUsername(String username);

    @Query(value = "select u.id id, u.username username, u.phone phone, u.address address, u.enabled enabled, group_concat(r.name) roles from user u, user_role i, role r where i.uid = u.id and i.rid = r.id group by u.id", nativeQuery = true)
    List<Map<String, Object>> findTable(Pageable pageable);

    @Query(value = "select username as username, phone as phone, address as address from User where username = :username")
    Map<String, Object> findMajorByUsername(@Param("username") String username);

    @Query(value = "select username as username, avatar as avatar, phone as phone, address as address from User where id = :id")
    Map<String, Object> findDetailById(@Param("id") int id);

}
