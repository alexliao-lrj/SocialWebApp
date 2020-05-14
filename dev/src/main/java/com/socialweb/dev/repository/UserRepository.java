package com.socialweb.dev.repository;

import com.socialweb.dev.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    List<User> findByUserId(String userId);
}
