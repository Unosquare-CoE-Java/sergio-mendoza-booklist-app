package com.training.booklist.dao;

import com.training.booklist.entities.CategoryEntity;
import org.springframework.data.repository.CrudRepository;

public interface CategoryDao extends CrudRepository<CategoryEntity, Long> {
}
