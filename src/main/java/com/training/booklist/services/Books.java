package com.training.booklist.services;


import com.training.booklist.dto.OldBookDto;
import com.training.booklist.dto.BookSearchApiResultDto;
import com.training.booklist.entities.BookEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface Books {
    List<BookEntity> getAllBooks();
    void saveBook(BookEntity book);
    void updateBook(Long id, BookEntity book);
    void deleteBook(Long id);
    void addCategory(Long categoryid, Long bookid);
    Map<String, Set<String>> getBooksByAuthor();
    Set<String> getBooksPublishedThisYear();
    Map<String, List<String>> getBooksByCategory();
    OldBookDto getOldestBook();
}
