package com.training.booklist.services;

import com.training.booklist.dao.CategoryDao;
import com.training.booklist.dto.CategoryDto;
import com.training.booklist.entities.CategoryEntity;
import com.training.booklist.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CategoryService implements Categories{
    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<CategoryEntity> getAllCategories() {
        List<CategoryEntity> categoryEntity = new ArrayList<>();
        categoryDao.findAll()
                .forEach(categoryEntity::add);
        return categoryEntity;
    }

    @Override
    public void saveCategory(CategoryDto category) {
        CategoryEntity categoryEntity = new CategoryEntity();

        categoryEntity.setName(category.getName());
        categoryDao.save(categoryEntity);
    }

    @Override
    public void deleteCategory(Long id) {
        if(categoryDao.existsById(id)) {
            categoryDao.deleteById(id);
        } else {
            throw new BadRequestException("Category already deleted");
        }
    }
/*
    @Override
    public void assignCategoryToBook(String name) {

    }*/
}
