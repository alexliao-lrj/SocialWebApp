package com.socialweb.dev.controller;

import com.socialweb.dev.entity.Post;
import com.socialweb.dev.entity.User;
import com.socialweb.dev.service.PostService;
import com.socialweb.dev.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @GetMapping(value = "/{userId}")
    public String userHome(@PathVariable String userId, Model model){
        //go to user's main page
        Subject curUser = SecurityUtils.getSubject();
        String curID = ((User)curUser.getPrincipal()).getUserId();
        //if cur user go to his home page
        if(userId.equals(curID)){
            return "redirect:/posts/";
        }
        List<Post> posts = postService.findPostByUserId(userId);
        model.addAttribute("postlist", posts);
        model.addAttribute("username", userId);
        return "userstream";
    }
}
