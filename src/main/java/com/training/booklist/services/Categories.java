package com.training.booklist.services;

import com.training.booklist.dto.CategoryDto;
import com.training.booklist.entities.CategoryEntity;

import java.util.List;

public interface Categories {
    List<CategoryEntity> getAllCategories();
    void saveCategory(CategoryDto category);
    void deleteCategory(Long id);
    //void assignCategoryToBook(String name);
}
