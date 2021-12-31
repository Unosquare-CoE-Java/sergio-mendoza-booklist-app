package com.training.booklist.services;

import com.training.booklist.dao.BookDao;
import com.training.booklist.dao.CategoryDao;
import com.training.booklist.dto.OldBookDto;
import com.training.booklist.entities.BookEntity;
import com.training.booklist.entities.CategoryEntity;
import com.training.booklist.exceptions.BadRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @Mock
    BookService bookService;
    @Mock
    BookDao bookDao;
    @Mock
    CategoryDao categoryDao;

    @Test
    void saveBook() {
        BookEntity book = new BookEntity();
        book.setAuthor("Manuel Lopez Michelone");
        book.setDescription("Analisis de los recursos informáticos que se usan para analizar la vida artificial");
        book.setIsbn("655435469494");
        book.setName("Jugando a ser dios, experimentos en vida artificial");
        book.setPublisher("UNAM");
        book.setPublishedDate(LocalDate.parse("2020-08-18"));
        doNothing().when(bookService).saveBook(book);
        bookService.saveBook(book);

        verify(bookService, times(1)).saveBook(book);
    }

    @Test
    void updateNonexistentBook() {
        Long mockId = 5000L;
        BookEntity book = new BookEntity();

        doThrow(BadRequestException.class).when(bookService).updateBook(mockId, book);
        Assertions.assertThrows(BadRequestException.class, () -> bookService.updateBook(mockId, book));
    }

    @Test
    void deleteNonexistentBook() {
        Long mockId = 5000L;

        doThrow(BadRequestException.class).when(bookService).deleteBook(anyLong());
        Assertions.assertThrows(BadRequestException.class, () -> bookService.deleteBook(mockId));
    }

    @Test
    void addCategory() {
        BookEntity book = new BookEntity();
        book.setAuthor("Manuel Lopez Michelone");
        book.setDescription("Analisis de los recursos informáticos que se usan para analizar la vida artificial");
        book.setIsbn("655435469494");
        book.setName("Jugando a ser dios, experimentos en vida artificial");
        book.setPublisher("UNAM");
        book.setPublishedDate(LocalDate.parse("2020-08-18"));
        bookDao.save(book);

        CategoryEntity category = new CategoryEntity();
        category.setName("Ciencia");
        categoryDao.save(category);

        Long bookId = book.getId();
        Long categoryId = category.getId();

        doNothing().when(bookService).addCategory(categoryId,bookId);
        bookService.addCategory(categoryId,bookId);

        verify(bookService,times(1)).addCategory(categoryId,bookId);
    }

    @Test
    void getOldestBook() {
        OldBookDto mockBook = new OldBookDto();
        mockBook.setAuthor("Max Planck");
        mockBook.setDescription("Introduction to theorical physics");
        mockBook.setName("General mechanics");
        mockBook.setPublishedDate(LocalDate.parse("1928-03-17"));

        Mockito.when(bookService.getOldestBook()).thenReturn(mockBook);

        BookEntity book = new BookEntity();
        book.setAuthor("Manuel Lopez Michelone");
        book.setDescription("Analisis de los recursos informáticos que se usan para analizar la vida artificial");
        book.setIsbn("655435469494");
        book.setName("Jugando a ser dios, experimentos en vida artificial");
        book.setPublisher("UNAM");
        book.setPublishedDate(LocalDate.parse("2020-08-18"));
        bookService.saveBook(book);

        BookEntity secondBook = new BookEntity();
        secondBook.setAuthor("Max Planck");
        secondBook.setDescription("Introduction to theorical physics");
        secondBook.setIsbn("46549687984321");
        secondBook.setName("General mechanics");
        secondBook.setPublisher("UNAM");
        secondBook.setPublishedDate(LocalDate.parse("1928-03-17"));
        bookService.saveBook(secondBook);

        OldBookDto result = bookService.getOldestBook();
        assertEquals(secondBook.getPublishedDate(), result.getPublishedDate());
    }

    @Test
    void shouldReturnBookFromThisYear() {
        Set<String> expectedResult = new HashSet<>();
        expectedResult.add("Enfoques sobre la globalización");
        Mockito.when(bookService.getBooksPublishedThisYear()).thenReturn(expectedResult);

        BookEntity book = new BookEntity();
        book.setAuthor("Octavio Luis Pineda");
        book.setDescription("Analisis comparativo sobre los principales enfoques de la globalización");
        book.setIsbn("65506071594");
        book.setName("Enfoques sobre la globalización");
        book.setPublisher("UNAM");
        book.setPublishedDate(LocalDate.now());
        bookDao.save(book);

        Set<String> result = bookService.getBooksPublishedThisYear();
        assertEquals(book.getName(), result.toArray()[0]);
    }
}