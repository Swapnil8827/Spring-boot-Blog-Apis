package com.blog.webapp.daos;


import com.blog.webapp.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDao extends JpaRepository<Category, Integer> {
}
