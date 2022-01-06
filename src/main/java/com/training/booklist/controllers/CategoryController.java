package com.training.booklist.controllers;

import com.training.booklist.dto.CategoryDto;
import com.training.booklist.entities.CategoryEntity;
import com.training.booklist.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    private CategoryService categories;

    @RequestMapping(method = RequestMethod.GET, value="/categories")
    public List<CategoryEntity> getAllCategories() {
        return categories.getAllCategories();
    }

    @RequestMapping(method = RequestMethod.POST, value="/categories")
    @PostMapping(
            value = "/saveCategory",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void saveCategory(@RequestBody CategoryEntity category){
        categories.saveCategory(category);
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/categories/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categories.deleteCategory(id);
    }
}
