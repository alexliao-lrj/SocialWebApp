package com.socialweb.dev.service;

import com.socialweb.dev.entity.User;
import com.socialweb.dev.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserByUserId(String userId){
        List<User> users = userRepository.findByUserId(userId);
        if(users.size() == 0){
            return null;
        }
        return users.get(0);
    }

    public void insertUser(User user){
        userRepository.save(user);
    }
}
