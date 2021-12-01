package com.training.booklist.services;

import com.training.booklist.dto.CategoryDto;

public interface Categories {
    void saveCategory(CategoryDto category);
    void deleteCategory(Long id);
    void assignCategoryToBook(String name);
}
