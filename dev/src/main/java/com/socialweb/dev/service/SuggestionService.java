package com.socialweb.dev.service;

import com.socialweb.dev.entity.Suggestion;
import com.socialweb.dev.repository.SuggestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SuggestionService {
    @Autowired
    private SuggestionRepository suggestRepo;

    public List<Integer> getSuggestionPostsByUser(String userId){
        List<Suggestion> suggestions = suggestRepo.findByUserId(userId);
        List<Integer> res = new ArrayList<>();
        int n = suggestions.size();
        for(int i = 0; i < Math.min(n, 5); i++){
            res.add(suggestions.get(i).getSuggestPostId());
        }
        return res;
    }
}
