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
    Map<String, Map<String, String>> getBooksByAuthor();
    Map<String, String> getBooksPublishedThisYear();
    Map<String, List<Map<String, String>>> getBooksByCategory();
    OldBookDto getOldestBook();
    Map<String, String> getNamesAndCoversFromList(List<BookEntity> books);
    Map<String, String> getNamesAndCoversFromSet(Set<BookEntity> books);
    OldBookDto createOldBookDto(BookEntity book);
    String replaceSpacesWithPlus(String s);
    BookSearchApiResultDto callToSearchByTitleApi(String book);
    String getBookCover(String book);
    void updateBooksWithCoverUrl();
}
