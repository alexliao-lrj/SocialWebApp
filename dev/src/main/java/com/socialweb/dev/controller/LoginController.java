package com.socialweb.dev.controller;

import com.socialweb.dev.entity.Post;
import com.socialweb.dev.entity.User;
import com.socialweb.dev.service.PostService;
import com.socialweb.dev.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, Model model){
        UsernamePasswordToken token = new UsernamePasswordToken(userId, password);
        Subject curUser = SecurityUtils.getSubject();
        try{
            curUser.login(token);
        }catch (IncorrectCredentialsException ice){
            model.addAttribute("error", "Wrong Password.");
        }catch (UnknownAccountException uae){
            model.addAttribute("error", "User doesn't exist.");
        }
        if(curUser.isAuthenticated()){
            model.addAttribute("currentUser", userId);
            return "redirect:/posts/";
            //return "redirect:/user/" + userId;
        }
        token.clear();
        return "login";
    }

    @GetMapping("/signup")
    public String signUp(){
        return "signup";
    }

    @PostMapping("/signup")
    public String signUp(String userId, String password, Model model){
        User user = userService.getUserByUserId(userId);
        if(user == null){
            userService.insertUser(new User(userId, password));
            model.addAttribute("error", "Successfully Registered! Login Now!");
            return "login";
        }
        model.addAttribute("error", "User already exists. Try another ID.");
        return "signup";
    }

    @GetMapping("/logout")
    public String logout(){
        return "login";
    }

    @GetMapping("/home")
    public String homePage(Model model){
        List<Post> posts = postService.findRecentPosts(10);
        model.addAttribute("postlist", posts);
        return "globalstream";
    }
}
