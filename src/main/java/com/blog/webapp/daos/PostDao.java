package com.blog.webapp.daos;

import com.blog.webapp.models.Category;
import com.blog.webapp.models.Post;
import com.blog.webapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostDao extends JpaRepository<Post, Integer> {

    public List<Post> findByUser(User user);
    public List<Post> findByCategory(Category category);

    @Query("select p from Post p where p.title like :key")
    public List<Post> getPostsByKeywords(@Param(value = "key") String keywords);
}
