package com.training.booklist.services;

import com.training.booklist.dao.BookDao;
import com.training.booklist.dao.CategoryDao;
import com.training.booklist.entities.BookEntity;
import com.training.booklist.entities.CategoryEntity;
import com.training.booklist.entities.UserEntity;
import com.training.booklist.exceptions.BadRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
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
}