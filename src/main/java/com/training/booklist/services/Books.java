package com.training.booklist.services;


import com.training.booklist.entities.BookEntity;
import com.training.booklist.entities.CategoryEntity;

import java.util.List;

public interface Books {
    List<BookEntity> getAllBooks();
    void saveBook(BookEntity book);
    void updateBook(Long id, BookEntity book);
    void deleteBook(Long id);
    void addCategory(Long categoryid, Long bookid);
}
