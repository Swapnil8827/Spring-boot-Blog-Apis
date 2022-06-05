package com.blog.webapp.services.impls;

import com.blog.webapp.daos.CategoryDao;
import com.blog.webapp.daos.PostDao;
import com.blog.webapp.daos.UserDao;
import com.blog.webapp.exceptions.ResourceNotFoundException;
import com.blog.webapp.models.Category;
import com.blog.webapp.models.Post;
import com.blog.webapp.models.User;
import com.blog.webapp.payloads.PostDto;
import com.blog.webapp.payloads.PostResponse;
import com.blog.webapp.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostDao postDao;

    @Autowired
    UserDao userDao;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId, String fileName) {

        User user = this.userDao
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId));
        Category category = this.categoryDao
                .findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));


        Post post = modelMapper.map(postDto, Post.class);
        post.setCategory(category);
        post.setUser(user);
        post.setImageUrl(fileName);
        post.setAddedDate(new Date());
        Post savedPost = postDao.save(post);

        return modelMapper.map(savedPost, PostDto.class);
    }

    @Override
    public PostDto getPostById(Integer postId) {

        Post post = this.postDao
                .findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post Id", postId));

        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy) {

        var pageable = PageRequest.of(pageNumber,pageSize, Sort.by(sortBy));
        var page = this.postDao.findAll(pageable);
        List<Post> posts = page.getContent();


        List<PostDto> postDtos = posts.stream()
                .map(post ->  this.modelMapper.map(post, PostDto.class))
                .toList();

        PostResponse postResponse = new PostResponse();

        postResponse.setPosts(postDtos);
        postResponse.setTotalElements(page.getTotalElements());
        postResponse.setTotalPages(page.getTotalPages());
        postResponse.setLast(page.isLast());

        return postResponse;
    }

    @Override
    public List<PostDto> getPostByUser(Integer userId) {

        User user = userDao.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","User Id", userId));

        List<Post> posts = this.postDao.findByUser(user);

        List<PostDto> postDtos = posts.stream()
                .map(post ->  this.modelMapper.map(post, PostDto.class))
                .toList();


        return postDtos;
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {
       Category category = categoryDao.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","Category Id", categoryId));

        List<Post> posts = this.postDao.findByCategory(category);

        List<PostDto> postDtos = posts.stream()
                .map(post ->  this.modelMapper.map(post, PostDto.class))
                .toList();


        return postDtos;
    }

    @Override
    public List<PostDto> getPostByKeyWords(String keyword) {

        List<Post> posts = postDao.getPostsByKeywords(keyword);

        List<PostDto> postDtos = posts.stream().map(post -> modelMapper.map(post, PostDto.class)).toList();

        return postDtos;
    }
}
