package com.blog.webapp.services;

import com.blog.webapp.payloads.PostDto;
import com.blog.webapp.payloads.PostResponse;

import java.util.List;

public interface PostService {

    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId, String fileName);

    public PostDto getPostById(Integer postId);

    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy);

    public List<PostDto> getPostByUser(Integer userId);

    public List<PostDto> getPostByCategory(Integer categoryId);

    public List<PostDto> getPostByKeyWords(String keyword);

}
