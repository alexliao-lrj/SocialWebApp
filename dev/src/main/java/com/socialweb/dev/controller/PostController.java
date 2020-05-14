package com.socialweb.dev.controller;

import com.socialweb.dev.entity.Dashboard;
import com.socialweb.dev.entity.Favorite;
import com.socialweb.dev.entity.Post;
import com.socialweb.dev.entity.User;
import com.socialweb.dev.service.PostService;
import com.socialweb.dev.service.SuggestionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private SuggestionService suggestionService;

    @GetMapping(value = "/")
    public String homePage(Model model){
        //List<Post> posts = postService.findAllPosts();
        List<Post> posts = postService.findRecentPosts(10);
        model.addAttribute("postlist", posts);
        model.addAttribute("post", new Post());

        Subject subject = SecurityUtils.getSubject();
        String userId = ((User)(subject.getPrincipal())).getUserId();
        List<Integer> favs = postService.findFavPostIdByUserId(userId);
        model.addAttribute("favlist", favs);
        return "globalstream";
    }

    @GetMapping(value = "/allposts")
    public String getAll(Model model){
        List<Post> posts = postService.findAllPosts();
        model.addAttribute("postlist", posts);
        model.addAttribute("post", new Post());
        return "searchresult";
    }

    @GetMapping(value = "/createpost")
    public String createPost(Model model){
        model.addAttribute("post", new Post());
        return "createpost";
    }

    @PostMapping(value = "/submitpost")
    public String submitPost(@ModelAttribute Post post, Model model){
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            post.setUserId(((User)subject.getPrincipal()).getUserId());
        }else{
            post.setUserId("Anonymous Guest");
        }
        postService.insertPost(post);
        return "redirect:/posts/";
    }

    @GetMapping(value = "/addfavorite")
    public String addFavorite(Integer postId){
        Subject subject = SecurityUtils.getSubject();
        String userId = ((User)(subject.getPrincipal())).getUserId();
        Favorite fav = new Favorite(postId, userId);
        postService.insertFavorite(fav);
        return "redirect:/posts/";
    }

    @GetMapping(value = "/submitsearch")
    public String queryPost(@RequestParam(value = "keyword")String keyword, Model model){
        Subject subject = SecurityUtils.getSubject();
        String userId = ((User)(subject.getPrincipal())).getUserId();
        //keyword changed to sentence for Natural Language Input
        List<Post> original = postService.findPostsByQuery(keyword);
        //sort results with calculated user profile
        List<Post> sorted = postService.sortByUserProfile(userId, original);
        model.addAttribute("postlist", sorted);
        return "searchresult";
    }

    @GetMapping(value = "/suggestion")
    public String getSuggestion(Model model){
        Subject subject = SecurityUtils.getSubject();
        String userId = ((User)(subject.getPrincipal())).getUserId();
        List<Integer> suggests = suggestionService.getSuggestionPostsByUser(userId);
        List<Post> posts = new ArrayList<>();
        for(Integer postId : suggests){
            posts.add(postService.findPostById(postId));
        }
        if(posts.size() == 0){
            posts = postService.findRecentPosts(5);
        }
        model.addAttribute("postlist", posts);
        return "searchresult";
    }

    @GetMapping(value = "/favoritelist")
    public String getFavoriteList(Model model){
        Subject subject = SecurityUtils.getSubject();
        String userId = ((User)(subject.getPrincipal())).getUserId();
        List<Post> posts = postService.findFavPostListByUserId(userId);
        model.addAttribute("postlist", posts);
        return "searchresult";
    }

    @GetMapping(value = "/searchuser")
    public String searchUser(Model model){
        return "searchuser";
    }

    @GetMapping(value = "/searchpost")
    public String searchPost(Model model){
        return "searchpost";
    }

    @GetMapping(value = "/submitsearchuser")
    public String queryPostByUser(@RequestParam(value = "userId")String userId, Model model){
        List<Post> posts = postService.findPostByUserId(userId);
        model.addAttribute("postlist", posts);
        return "searchresult";
    }

    @GetMapping(value = "/dashboard")
    public String getDashboard(Model model){
        Dashboard dashboard = postService.getDashboard();
        model.addAttribute("dashboard", dashboard);
        return "dashboard";
    }
}
