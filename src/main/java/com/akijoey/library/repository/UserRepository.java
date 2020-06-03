package com.akijoey.library.repository;

import com.akijoey.library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    @Query(value = "select username as username, avatar as avatar, phone as phone, address as address from User where username = :username")
    Map<String, Object> findDetailByUsername(@Param("username") String username);

}
