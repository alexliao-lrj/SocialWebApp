package com.socialweb.dev.repository;

import com.socialweb.dev.entity.UserProfile;
import com.socialweb.dev.entity.UserProfileKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserProfileRepository extends JpaRepository<UserProfile, UserProfileKey> {
    List<UserProfile> findByUserId(String userId);
}
