package com.socialweb.dev.repository;

import com.socialweb.dev.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByContentContainingOrderByCreateTimeDesc(String content);
    List<Post> findByUserIdOrderByCreateTimeDesc(String userId);
    List<Post> getByOrderByCreateTimeDesc();
    Optional<Post> findById(Integer id);

    @Query(value = "select * from posts p order by create_time desc limit :size", nativeQuery = true)
    List<Post> getByOrderByCreateTimeDescLimitTo(@Param("size") int size);

    int countBy();
    int countByCreateTimeAfter(Date start);

}
