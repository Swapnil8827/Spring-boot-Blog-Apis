package com.blog.webapp.controllers;

import com.blog.webapp.models.Post;
import com.blog.webapp.payloads.ApiResponse;
import com.blog.webapp.payloads.PostDto;
import com.blog.webapp.payloads.PostResponse;
import com.blog.webapp.services.FileService;
import com.blog.webapp.services.PostService;
import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    FileService fileService;

    @Value("${project.image}")
    private String path;


    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@PathVariable Integer userId,
                                              @PathVariable Integer categoryId,
                                              @RequestParam("title") String title,
                                              @RequestParam("content") String content,
                                              @RequestParam("image") MultipartFile image) throws IOException {

        String fileName = this.fileService.uploadImage(path, image);

        PostDto postDto = new PostDto();
        postDto.setTitle(title);
        postDto.setContent(content);

        PostDto post = postService.createPost(postDto, userId, categoryId, fileName);

        return new ResponseEntity<PostDto>(post, HttpStatus.CREATED);
    }

    @GetMapping("/posts")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy) {

        Class<Post> postClass = Post.class;

        Field[] declaredFields = postClass.getDeclaredFields();

        boolean isFieldPresent = false;
        for (Field field : declaredFields) {
            if (sortBy.equalsIgnoreCase(field.getName())) {
                isFieldPresent = true;
                break;
            }
        }

        if (!isFieldPresent) {
            sortBy = "id";
        }

        PostResponse postResponse = this.postService.getAllPost(pageNumber, pageSize, sortBy);


        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<Map<String, List<PostDto>>> getPostsByUser(@PathVariable Integer userId) {

        List<PostDto> allPost = this.postService.getPostByUser(userId);

        Map<String, List<PostDto>> map = new HashMap<>();
        map.put("posts", allPost);

        return ResponseEntity.ok(map);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<Map<String, List<PostDto>>> getPostsByCategory(@PathVariable Integer categoryId) {

        List<PostDto> allPost = this.postService.getPostByCategory(categoryId);

        Map<String, List<PostDto>> map = new HashMap<>();
        map.put("posts", allPost);

        return ResponseEntity.ok(map);
    }


    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {

        PostDto post = postService.getPostById(postId);

        return ResponseEntity.ok(post);
    }

    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<Map<String, List<PostDto>>> getPostBySearch(@PathVariable String keywords) {

        List<PostDto> posts = postService.getPostByKeyWords("%" + keywords + "%");

        Map<String, List<PostDto>> map = new HashMap<>();
        map.put("posts", posts);

        return ResponseEntity.ok(map);
    }

    @GetMapping(value="/post/image/{imageName}",produces= MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName")String imageName, HttpServletResponse response)throws IOException {
        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
}
