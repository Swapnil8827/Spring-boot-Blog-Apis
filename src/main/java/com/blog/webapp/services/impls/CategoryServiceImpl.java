package com.blog.webapp.services.impls;

import com.blog.webapp.daos.CategoryDao;
import com.blog.webapp.exceptions.ResourceNotFoundException;
import com.blog.webapp.models.Category;
import com.blog.webapp.payloads.CategoryDto;
import com.blog.webapp.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        Category category = modelMapper.map(categoryDto, Category.class);

        Category savedCategory = categoryDao.save(category);

        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {

       Category category = categoryDao
                .findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category", "category Id", categoryId));

       category.setName(categoryDto.getName());
       category.setDescription(categoryDto.getDescription());

       Category updatedCategory=categoryDao.save(category);

        return modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {

        var category = categoryDao
                .findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category", "category Id", categoryId));

        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategory() {

        List<Category> categories = categoryDao.findAll();

        return categories
                .stream()
                .map(this::categoryToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(Integer categoryId) {

        Category category = categoryDao
                .findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category Id", categoryId));

        categoryDao.delete(category);
    }

    public Category dtoToCategory(CategoryDto categoryDto){
        return modelMapper.map(categoryDto, Category.class);
    }
    public CategoryDto categoryToDto(Category category){
        return modelMapper.map(category, CategoryDto.class);
    }

}
