package com.blog.webapp.controllers;

import com.blog.webapp.daos.CategoryDao;
import com.blog.webapp.models.Category;
import com.blog.webapp.payloads.ApiResponse;
import com.blog.webapp.payloads.CategoryDto;
import com.blog.webapp.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/category")
public class CategoryController {


    @Autowired
    CategoryService categoryService;

    @PostMapping("")
    public ResponseEntity<ApiResponse> createCategory(@RequestBody CategoryDto categoryDto){

        categoryService.createCategory(categoryDto);

        return new ResponseEntity<>(new ApiResponse("Category Created Successfully", true), HttpStatus.CREATED);

    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,@PathVariable Integer categoryId){

        categoryService.updateCategory(categoryDto, categoryId);

        return  ResponseEntity.ok(categoryDto);

    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable Integer id ){

        CategoryDto categoryDto = categoryService.getCategoryById(id);

        return ResponseEntity.ok(categoryDto);

    }
    @GetMapping("")
    public ResponseEntity<Map<String, List<CategoryDto>>> getAllCategories(){

        List<CategoryDto> categoryDtos = categoryService.getAllCategory();

        return ResponseEntity.ok(Map.of("categories", categoryDtos));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer id){

        categoryService.deleteCategory(id);

        return  ResponseEntity.ok(new ApiResponse("Category Deleted Successfully", true));
    }
}
