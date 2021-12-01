package com.training.booklist.controllers;

import com.training.booklist.dto.CategoryDto;
import com.training.booklist.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryController {
    @Autowired
    private CategoryService categories;

    @RequestMapping(method = RequestMethod.POST, value="/categories")
    @PostMapping(
            value = "/saveBook",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void saveCategory(@RequestBody CategoryDto category){
        categories.saveCategory(category);
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/categories/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categories.deleteCategory(id);
    }
}
