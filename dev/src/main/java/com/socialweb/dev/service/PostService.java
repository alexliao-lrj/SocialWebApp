package com.socialweb.dev.service;

import com.socialweb.dev.entity.Dashboard;
import com.socialweb.dev.entity.Favorite;
import com.socialweb.dev.entity.Post;
import com.socialweb.dev.entity.UserProfile;
import com.socialweb.dev.repository.FavoriteRepository;
import com.socialweb.dev.repository.PostRepository;
import com.socialweb.dev.repository.UserProfileRepository;
import com.socialweb.dev.util.NLIUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepo;

    @Autowired
    private FavoriteRepository favoriteRepo;

    @Autowired
    private UserProfileRepository profileRepo;

    @Autowired
    private EntityManager entityManager;

    public List<Post> findAllPosts(){
        List<Post> posts = postRepo.getByOrderByCreateTimeDesc();
        return posts;
    }

    public Post findPostById(Integer postId){
        Optional<Post> post = postRepo.findById(postId);
        return post.get();
    }

    public List<Post> findRecentPosts(int size){
        List<Post> posts = postRepo.getByOrderByCreateTimeDescLimitTo(size);
        return posts;
    }

    public List<Post> findPostsByQuery(String str){
        List<Post> posts = new ArrayList<>();
        String queryContent = NLIUtil.getQuery("content", "AND", str);
        List<Post> content = entityManager.createNativeQuery(queryContent, Post.class).getResultList();
        posts.addAll(content);
        if(posts.size() < 5){
            String queryOR = NLIUtil.getQuery("content", "OR", str);
            posts = entityManager.createNativeQuery(queryOR, Post.class).getResultList();
        }
        //still not enough, randomly give user results
        if(posts.size() == 0){
            posts = postRepo.getByOrderByCreateTimeDescLimitTo(10);
        }
        return posts;
    }

    public void insertPost(Post post){
        postRepo.save(post);
    }

    public void insertFavorite(Favorite fav){
        favoriteRepo.save(fav);
    }

    public List<Favorite> findFavlistByUserId(String userId){
        List<Favorite> fav = favoriteRepo.findByUserId(userId);
        return fav;
    }

    public List<Post> findFavPostListByUserId(String userId){
        List<Favorite> fav = findFavlistByUserId(userId);
        List<Post> posts = new ArrayList<>();
        for(Favorite f : fav){
            posts.add(findPostById(f.getPostId()));
        }
        return posts;
    }

    public List<Integer> findFavPostIdByUserId(String userId){
        List<Favorite> temp = findFavlistByUserId(userId);
        List<Integer> favs = new ArrayList<>();
        for(Favorite f : temp){
            favs.add(f.getPostId());
        }
        return favs;
    }

    //get user profile vector by userId from database
    public Map<String, Double> getUserProfile(String userId){
        List<UserProfile> profiles = profileRepo.findByUserId(userId);
        Map<String, Double> map = new HashMap<>();
        for(UserProfile up : profiles){
            String w = up.getWord();
            Double s = up.getScore();
            map.put(w, s);
        }
        return map;
    }

    //get score between user profile vector and post content
    public double getScore(Map<String, Double> profile, Post post){
        Set<String> keyWords = NLIUtil.getTokens(post.getContent());
        double score = 0;
        for(String kw : keyWords){
            if(profile.containsKey(kw)){
                score += profile.get(kw);
            }
        }
        return score;
    }

    //sort results by calculated user profile
    public List<Post> sortByUserProfile(String userId, List<Post> posts){
        Map<String, Double> profile = getUserProfile(userId);
        Map<Integer, Double> pidToScore = new HashMap<>();
        for(Post p : posts){
            double s = getScore(profile, p);
            pidToScore.put(p.getId(), s);
        }
        Collections.sort(posts, (o1, o2)->Double.compare(pidToScore.get(o2.getId()), pidToScore.get(o1.getId())));
        return posts;
    }

    public List<Post> findPostByKeyword(String keyword){
        //content orderby time
        List<Post> posts = postRepo.findByContentContainingOrderByCreateTimeDesc(keyword);
        return posts;
    }

    public List<Post> findPostByUserId(String userId){
        List<Post> posts = postRepo.findByUserIdOrderByCreateTimeDesc(userId);
        return posts;
    }

    public Dashboard getDashboard(){
        int total = postRepo.countBy();
        Date start = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        start = calendar.getTime();
        int today = postRepo.countByCreateTimeAfter(start);
        calendar.add(Calendar.MONTH, -1);
        start = calendar.getTime();
        int month = postRepo.countByCreateTimeAfter(start);
        calendar.add(Calendar.YEAR, -1);
        start = calendar.getTime();
        int year = postRepo.countByCreateTimeAfter(start);
        return new Dashboard(total, today, month, year);
    }
}
