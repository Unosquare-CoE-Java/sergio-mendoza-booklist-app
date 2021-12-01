package com.training.booklist.services;

import com.training.booklist.dto.BookDto;
import com.training.booklist.entities.BookEntity;

import java.util.List;

public interface Books {
    List<BookEntity> getAllBooks();
    void saveBook(BookDto book);
    void updateBook(Long id, BookDto book);
    void deleteBook(Long id);
}
