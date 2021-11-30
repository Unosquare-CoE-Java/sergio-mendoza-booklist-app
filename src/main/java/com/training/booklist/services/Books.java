package com.training.booklist.services;

import com.training.booklist.dto.BookDto;

import java.util.List;

public interface Books {
    List<BookDto> getBooks();
    void saveBook(BookDto book);
    void updateBook(String name, BookDto book);
    void deleteBook(String name);
}
