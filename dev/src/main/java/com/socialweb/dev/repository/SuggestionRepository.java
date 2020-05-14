package com.socialweb.dev.repository;

import com.socialweb.dev.entity.Suggestion;
import com.socialweb.dev.entity.SuggestionKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SuggestionRepository extends JpaRepository<Suggestion, SuggestionKey> {
    List<Suggestion> findByUserId(String userId);
}
