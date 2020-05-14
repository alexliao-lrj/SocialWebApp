package com.socialweb.dev.repository;

import com.socialweb.dev.entity.FavKey;
import com.socialweb.dev.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface FavoriteRepository extends JpaRepository<Favorite, FavKey> {
    //get a user's fav list
    List<Favorite> findByUserId(String userId);
}
