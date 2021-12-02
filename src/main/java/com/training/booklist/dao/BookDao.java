package com.training.booklist.dao;

import com.training.booklist.entities.BookEntity;
import org.springframework.data.repository.CrudRepository;

public interface BookDao extends CrudRepository<BookEntity, Long> {
    BookEntity getBookEntityById(Long id);
}
